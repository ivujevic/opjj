/**
 * 
 */
package hr.fer.zemris.java.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Policy.Parameters;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Ivan
 * 
 */
public class RequestContext {

	private OutputStream outputStream;
	private Charset charset;

	public String encoding = "UTF-8";
	public int statusCode = 200;
	public String statusText = "OK";
	public String mimeType = "text/html";
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters = new LinkedHashMap<>();
	Map<String, String> persistentParameters;
	List<RCCookie> outputCookies;
	private boolean headerGenerated = false;

	/**
	 * Konstruktor
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 */

	public RequestContext(final OutputStream outputStream, final Map<String, String> parameters,
			final Map<String, String> persistentParameters, final List<RCCookie> outputCookies) {
		super();
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
	}

	/**
	 * Metoda koja vraća vrijednost vrijednost iz mape parametara
	 * 
	 * @param name
	 *            ključ u mapi za koji se vraća vrijednost
	 * @return vrijednost iz mape
	 */
	public final String getParameter(final String name) {
		return parameters.get(name);
	}

	/**
	 * Metodak koja vraća skup svih imena parametara koji se nalaze u mapi
	 * 
	 * @return skup imena parametara
	 */
	public final Set<String> getParameterNames() {
		Set<String> set = new LinkedHashSet<>();
		for (Entry<String, String> p : parameters.entrySet()) {
			set.add(p.getKey());
		}
		return set;
	}

	/**
	 * Metoda koja vraća vrijednost iz mape persistentParameter
	 * 
	 * @param name
	 *            ime za koji se vrijednost traži
	 * @return vrijednost iz mape za zadani ključ
	 */
	public final String getPersistentParameter(final String name) {
		return persistentParameters.get(name);
	}

	public final Set<String> getPersistentParameterNames() {
		Set<String> set = new LinkedHashSet<>();
		for (Entry<String, String> p : persistentParameters.entrySet()) {
			set.add(p.getKey());
		}
		return set;
	}

	/**
	 * Metoda koja sprema vrijednost u mapu.
	 * 
	 * @param name
	 *            Ključ pod kojim se vrijednost sprema.
	 * @param value
	 *            Vrijednost koja se sprema u mapu
	 */
	public final void setPersistentParameter(final String name, final String value) {
		if (persistentParameters.containsKey(name)) {
			persistentParameters.remove(name);
		}
		persistentParameters.put(name, value);
	}

	/**
	 * Metoda koja briše element iz mape.
	 * 
	 * @param name
	 *            Ključ pod kojime je element pohranjen u mapi
	 */
	public final void removePersistenetParameter(final String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Metoda koja vraća vrijednost pohranjenu u mapi temmporaryParameters
	 * 
	 * @param name
	 *            Ključ pod kojim je vrijednost pohranjena
	 * @return vrijednost koja je pohranjen pod danim ključem
	 */
	public final String getTemporaryParameter(final String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Metoda koja vraća skup svih imena u mapi temporaryParameter
	 * 
	 * @return skup imena
	 */
	public final Set<String> getTemporaryParameterNames() {
		Set<String> set = new LinkedHashSet<>();
		for (Entry<String, String> p : temporaryParameters.entrySet()) {
			set.add(p.getKey());
		}
		return set;
	}

	/**
	 * Metoda koja dodaje vrijednost u mapu.
	 * 
	 * @param name
	 *            Ključ pod kojim se vrijednost dodaje
	 * @param value
	 *            vrijednost koja se dodaje u mapu
	 */
	public final void setTemporaryParameter(final String name, final String value) {
		if (temporaryParameters.containsKey(name)) {
			temporaryParameters.remove(name);
		}
		temporaryParameters.put(name, value);
	}

	public final void removeTemporaryParameter(final String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Piše podatke u izlaz.
	 * 
	 * @param data
	 *            podatci koji se pišu
	 * @return referencu na samog sebe
	 * @throws IOException
	 */
	public final synchronized RequestContext write(final byte[] data) {
		if (headerGenerated == false) {
			try {
				writeHeader(data.length);
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			outputStream.write(data);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Pišu podatke u izlaz
	 * 
	 * @param text
	 *            tekst koji se piše
	 * @return referencu na samog sebe
	 * @throws IOException
	 */
	public final synchronized RequestContext write(final String text) {
		if (headerGenerated == false) {
			writeHeader(text.getBytes().length);
		}

		try {
			outputStream.write(text.getBytes(charset));
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public final void addRCCookie(final RCCookie cookie) {
		if (headerGenerated == true) {
			throw new RuntimeException();
		}

		outputCookies.add(cookie);
	}

	/**
	 * Metoda koja dodaje naslov u outputStream
	 * 
	 * @param length
	 * @throws IOException
	 */
	private void writeHeader(final int length) {
		StringBuilder header = new StringBuilder();
		header.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
		header.append("Content-Type: ").append(mimeType).append(" Content-Length: ").append(length)
		.append("\r\n");
		if (mimeType.startsWith("text/")) {
			header.append("; charset= ").append(encoding).append("\r\n");
		}
		for (RCCookie c : outputCookies) {
			header.append("Set-Cookie: ").append(c.name).append("=\"").append(c.value).append("\"");
			if (c.domain != null) {
				header.append("; Domain=").append(c.domain);
			}
			if (c.path != null) {
				header.append("; Path =").append(c.path);
			}
			if (c.maxAge != null) {
				header.append("; maxAge=").append(String.valueOf(c.maxAge));
			}
			header.append("\r\n");
		}
		header.append("\n");
		charset = Charset.forName(encoding);
		try {
			outputStream.write(header.toString().getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		headerGenerated = true;
	}

	/**
	 * Vraća mapu sadržaja TemporaryParameters.
	 * 
	 * @return Mapa TemporaryParameters
	 */
	public final Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Postavlja referencu TemporaryParameters na predanu mapu.
	 * 
	 * @param temporaryParameters
	 *            mapa na koju se postavlja referenca
	 */
	public final void setTemporaryParameters(final Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Vraća mapu sadržaja PersistentParameters
	 * 
	 * @return Mapa PersistentParameters
	 */
	public final Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Postavlja referencu PersistentParameters na predanu mapu.
	 * 
	 * @param persistentParameters
	 *            mapa na koju se postavlja referenca
	 */
	public final void setPersistentParameters(final Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Vraća mapu sadržaja Parameters
	 * 
	 * @return Mapa parameters
	 */
	public final Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Postavlja enkodiranje.
	 * 
	 * @param encoding
	 *            Enkodiranje koje se postavlja.
	 */
	public final void setEncoding(final String encoding) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.encoding = encoding;
	}

	/**
	 * Postavlja status koji se generira u zaglavlju
	 * 
	 * @param statusCode
	 *            broj koji označava status koji se generira u zaglavlje.
	 */
	public final void setStatusCode(final int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Postavlja tekst statusa koji se generira u zaglavlju.
	 * 
	 * @param statusText
	 *            tekst koji predstavlja status koji se generira u zaglavlju.
	 */
	public final void setStatusText(final String statusText) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Postavlja mimeType koji se generira u zaglavlju.
	 * 
	 * @param mimeType
	 *            postavlja se u zaglavlju i predstavlje MimeType
	 */
	public final void setMimeType(final String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Razred koji predstavlja Cookie
	 * 
	 * @author Ivan
	 * 
	 */
	public static class RCCookie {
		/**
		 * Ime Cookia
		 */
		String name;
		/**
		 * Vrijednost Cookia
		 */
		String value;

		/**
		 * Domena s koje je učitan
		 */
		String domain;

		/**
		 * Staza
		 */
		String path;

		/**
		 * Maksimalno pamćenje Cookia
		 */
		Integer maxAge;

		public RCCookie(final String name, final String value, final Integer maxAge, final String domain, final String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Vraća ime cookia
		 * 
		 * @return ime
		 */
		public final String getName() {
			return name;
		}

		/**
		 * Vraća vrijednost cookia
		 * 
		 * @return vrijednost
		 */
		public final String getValue() {
			return value;
		}

		/**
		 * Vraća domenu cookia
		 * 
		 * @return domena
		 */
		public final String getDomain() {
			return domain;
		}

		/**
		 * Vraća stazu cookia
		 * 
		 * @return staza
		 */
		public final String getPath() {
			return path;
		}

		/**
		 * Vraća maksimalno zadržavanje cookia
		 * 
		 * @return makismalno zadržavanje
		 */
		public final Integer getMaxAge() {
			return maxAge;
		}

	}
}
