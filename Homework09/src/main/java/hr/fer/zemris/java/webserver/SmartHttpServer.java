/**
 * 
 */
package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Razred u kojem se implementira jednostavni web server kao dio 4.zadatka iz domaće zadaće
 * 
 * @author Ivan
 * 
 */
public class SmartHttpServer {

	private static final int FORBIDDEN = 403;
	private static final int INVALID_HEADER = 400;
	private static final int NOT_EXIST_FILE = 404;
	private static final int DEFAULT = 200;
	private static final int SLEEP_TIME = 5;
	/**
	 * Adresa na kojoj server sluša
	 */
	private String adress;

	/**
	 * Port na kojem server sluša
	 */
	private int port;
	/**
	 * Broj dretvi koje se koriste
	 */
	private int workerThreads;

	/**
	 * Trajanje u sekundama
	 */
	private int sessionTimeout;
	/**
	 * mime svojstva
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Dretva koja izvodi akcije na serveru
	 */
	private ServerThread serverThread;

	/**
	 * Executor u koji se spremaju dretve
	 */
	private ExecutorService threadPool;

	/**
	 * Staza na mjesto gdje se nalaze svi direktoriji koji se mogu dohvatiti na serveru
	 */
	private Path documentRoot;

	/**
	 * Mapa u koju se spremaju workersi
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Mapa u koju se spremaju sjednice
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Generira slučajni broj
	 */
	private Random sessionRandom = new Random();

	/**
	 * Metoda koja se pokreće prilikom pokretanja programa, kao argument prima naziv datoteke gdje se nalaze
	 * svojstva servera
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}

	/**
	 * Konstruktor za server
	 * 
	 * @param configFileName
	 *            ime datoteke u kojoj su svojstva servera
	 */
	public SmartHttpServer(final String configFileName) {
		workersMap = new HashMap<>();
		Properties serverProperties = new Properties();
		Properties mimeProperties = new Properties();
		Properties workersProperties = new Properties();

		try {
			serverProperties.load(new FileInputStream(new File(configFileName)));
			mimeProperties.load(new FileInputStream(new File(serverProperties
					.getProperty("server.mimeConfig"))));
			workersProperties.load(new FileInputStream(new File(serverProperties
					.getProperty("server.workers"))));
		} catch (IOException e) {
		}
		this.adress = serverProperties.getProperty("server.adress");
		this.port = Integer.valueOf(serverProperties.getProperty("server.port"));
		this.workerThreads = Integer.valueOf(serverProperties.getProperty("server.workerThreads"));
		this.documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		this.sessionTimeout = Integer.valueOf(serverProperties.getProperty("session.timeout"));

		// učitaj mimeProperties
		for (Entry<Object, Object> p : mimeProperties.entrySet()) {
			mimeTypes.put((String) p.getKey(), (String) p.getValue());
		}

		// učitaj workersProperties
		for (Entry<Object, Object> p : workersProperties.entrySet()) {
			String path = String.valueOf(p.getKey());
			String fqcn = String.valueOf(p.getValue());
			Class<?> referenceToClass;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	protected final synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			Thread killThread = new KillThread();
			killThread.setDaemon(true);
			killThread.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.start();
	}

	/**
	 * Zaustavlja server dretvu
	 */
	protected final synchronized void stop() {
		serverThread.work = false;
		threadPool.shutdown();
	}

	/**
	 * Dretva koja briše Cookie kojima je isteklo vrijeme
	 * 
	 */
	protected class KillThread extends Thread {

		@Override
		public final void run() {
			while (true) {
				synchronized (sessions) {
					Set<Entry<String, SessionMapEntry>> temp = new HashSet<>(sessions.entrySet());
					for (Entry<String, SessionMapEntry> e : temp) {
						if (new Date().after(new Date(e.getValue().validUntil))) {
							sessions.remove(e.getKey());
						}
					}
				}
				try {
					sleep(SLEEP_TIME * 1000*60);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	protected class ServerThread extends Thread {

		/**
		 * Dok je true dretva se vrti u beskonačnoj petlji
		 */
		boolean work = true;

		@Override
		public final void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (work) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
			}

		}
	}

	private class ClientWorker implements Runnable {

		/**
		 * Referenca na socket koji je otvoren.
		 */
		private Socket csocket;

		/**
		 * ulaz socketa.
		 */
		private PushbackInputStream istream;

		/**
		 * Izlaz socketa.
		 */
		private OutputStream ostream;
		/**
		 * Verzija s kojom se radi.
		 */
		private String version;

		/**
		 * Metoda koju šalje klijent.
		 */
		private String method;

		/**
		 * Mapa parametara.
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Mapa perParams.
		 */
		private Map<String, String> permPrams = new HashMap<>();

		/**
		 * Lista u koju se spremaju Cookie-i.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * SID koji je dodjeljen svakom klijentu koji pristupa.
		 */
		private String SID;

		/**
		 * Konstruktor ClientWorkera
		 */
		public ClientWorker(final Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = this.csocket.getOutputStream();

				List<String> request = readRequest();

				checkSession(request);

				String firstLine = request.get(0);
				String[] firstLineSplit = firstLine.trim().split(" ");

				this.method = firstLineSplit[0];
				String requestedPath = firstLineSplit[1];
				this.version = firstLineSplit[2];

				if (!this.method.equals("GET")
						|| (!this.version.equals("HTTP/1.0") && !this.version.equals("HTTP/1.1"))) {
					error(INVALID_HEADER);
				}
				String path = requestedPath.split("\\?")[0];

				if (requestedPath.contains("?")) {
					String paramString = requestedPath.split("\\?")[1];
					parseParameters(paramString);
				}
				Path reqPath = Paths.get(documentRoot.toString() + path);

				File file = reqPath.toFile();
				String extension = "";
				String name = file.getName();
				extension = name.substring(name.lastIndexOf('.') + 1);
				String mime = mimeTypes.get(extension);
				if (mime == null) {
					mime = "application/octet-stream";
				}
				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				rc.setMimeType(mime);
				if (path.startsWith("/ext")) {
					String className = path.substring(1).split("/")[1];
					try {
						Class<?> c = this.getClass().getClassLoader()
								.loadClass("hr.fer.zemris.java.webserver.workers." + className);
						Object newObject = c.newInstance();
						IWebWorker iww = (IWebWorker) newObject;
						iww.processRequest(rc);
						csocket.close();
						return;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}
				if (workersMap.containsKey(path)) {
					workersMap.get(path).processRequest(rc);
					ostream.flush();
					csocket.close();
					return;
				}
				if (!file.exists() || !file.isFile() || !file.canRead()) {
					if (!file.getName().contains("favicon")) {
						error(NOT_EXIST_FILE);
					}
				}

				else {

					if (extension.equals("smscr")) {
						readScripts(file, rc);
					}
					else {
						BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));

						ByteArrayOutputStream bos = new ByteArrayOutputStream();

						byte[] buffer = new byte[1024];
						int length;
						while (true) {
							length = inStream.read(buffer);
							if (length == -1) {
								break;
							}
							bos.write(buffer, 0, length);
						}
						rc.write(bos.toByteArray());
						inStream.close();
					}
				}
				ostream.flush();
				csocket.close();
			} catch (IOException e) {
			}
		}

		/**
		 * 
		 * @param request
		 *            Lista linija iz zaglavlja.
		 */
		private void checkSession(final List<String> request) {

			String sidCandidate = null;
			for (String l : request) {
				if (l.startsWith("Cookie")) {
					Pattern patern = Pattern.compile("sid=\"[a-z]*\"");
					Matcher matcher = patern.matcher(l);
					if (matcher.find()) {
						sidCandidate = matcher.group().split("=")[1];
						sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1);
					}
				}
			}
			SessionMapEntry entry;
			if (sidCandidate == null) {
				sidCandidate = createNewEntry();
			}
			else {
				synchronized (sessions) {
					entry = sessions.get(sidCandidate);
				}
				
				if (entry.validUntil < new Date().getTime() / 1000) {
					
					synchronized (sessions) {
						sessions.remove(sidCandidate);
					}
					createNewEntry();
				}
				else {
					entry.validUntil = new Date().getTime() / 1000 + sessionTimeout;
				}
			}
			synchronized (sessions) {
				permPrams = sessions.get(sidCandidate).map;
			}
		}

		/**
		 * Metoda u kojoj se kreira novi sid ukoliko nije pronađen u zaglavlju
		 */
		private String createNewEntry() {

			StringBuilder sid = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				// dodaj jedan znak iz skupa [a-z]
				sid.append((char) (sessionRandom.nextInt(26) + 97));
			}

			// vrijeme iz milisekunda pretvori u sekunde
			long validUntil = (new Date().getTime()) / 1000 + sessionTimeout;

			SessionMapEntry entry = new SessionMapEntry(sid.toString(), validUntil);
			synchronized (sessions) {
				sessions.put(sid.toString(), entry);
			}
			outputCookies.add(new RCCookie("sid", sid.toString(), (int) validUntil, adress, "/"));
			return sid.toString();
		}

		/**
		 * Metoda koja čita smscr skripte i ispsiuje rezultat klijentu
		 * 
		 * @param file
		 *            Skripta koja se čita
		 * @param rc
		 *            referenca na RequestContext instancu
		 */
		private void readScripts(final File file, final RequestContext rc) {
			try {
				// Postavi na početni mimeType
				rc.setMimeType("text/plain");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
						StandardCharsets.UTF_8));
				StringBuilder builder = new StringBuilder();
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					builder.append(line).append("\n");
				}
				br.close();
				new SmartScriptEngine(new SmartScriptParser(builder.toString()).getDocumentNode(), rc)
						.execute();
			} catch (FileNotFoundException e) {
				error(NOT_EXIST_FILE);
			} catch (IOException e) {
				error(NOT_EXIST_FILE);
			} catch (SmartScriptParserException e) {
			}
		}

		/**
		 * Metoda koja izdvaja parametre i dodaje ih u mapu.
		 * 
		 * @param paramString
		 *            String iz kojeg se izdvajaju parametri
		 */
		private void parseParameters(final String paramString) {
			String[] parameters = paramString.split("\\&");
			for (String p : parameters) {
				this.params.put(p.split("=")[0], p.split("=")[1]);
			}

		}

		/**
		 * Metoda koja čita zaglavlje klijenta koji se spaja
		 * 
		 * @return
		 */
		private List<String> readRequest() {
			List<String> list = new ArrayList<>();
			Scanner sc = new Scanner(istream, "UTF-8");

			while (true) {
				String line = sc.nextLine();
				if (line == null || line.trim().isEmpty()) {
					break;
				}
				list.add(line);
			}
			return list;
		}

		/**
		 * Metoda koja ispisuje pogreške
		 * 
		 * @param i
		 *            Kod pokreške
		 */
		public void error(final int i) {
			StringBuilder builder = new StringBuilder();
			switch (i) {
				case FORBIDDEN:
					builder.append("Forbidden pogreka");
					break;
				case INVALID_HEADER:
					builder.append("Primio sam neispravno zaglavlje");
				case NOT_EXIST_FILE:
					builder.append("Tražena datoteka ne postoji");
				default:
					break;
			}
			try {
				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				rc.setStatusCode(i);
				rc.setStatusText("Pogreška");
				rc.write(builder.toString().getBytes(StandardCharsets.UTF_8));
				ostream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String, String> map;

		public SessionMapEntry(final String sid, final long validUntil) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = new ConcurrentHashMap<>();
		}
	}

}
