/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipFile;

/**
 * Razred u kojem se izvode instrukcije, odnosno provjerava se je li predani program sadrži sve potrebno.
 * 
 */
public class FCFileVerifier {

	/**
	 * Staza do datoteke koja se čita.
	 */
	private static File file;

	/**
	 * Ime koje bi datoteka trebala imati.
	 */
	private static String fileName;

	/**
	 * Program koji se učita iz datoteke.
	 */
	private static String program;

	/**
	 * Označava je li se dogodio prekid ili ne.
	 */
	private static int stop = 0;

	/**
	 * Mapa u koju se pohranjeni podaci o studentima.
	 */
	private static Map<String, Object> initialData;

	/**
	 * Svi pathovi iz zip fajla.
	 */
	private static Set<String> content = new LinkedHashSet<>();

	/**
	 * Vrijednost definiranih varijabli.
	 */
	private static Map<String, String> defValue = new LinkedHashMap<>();

	/**
	 * Mapa u koju se pohranjuju dojavljene pogreške.
	 */
	private static Set<String> errors = new LinkedHashSet<>();

	/**
	 * @param file
	 *            Staza do datoteke
	 * @param fileName
	 *            Ime datoteke
	 * @param program
	 *            Učitani program
	 * @param initialData
	 *            Podaci o studentima
	 */
	public FCFileVerifier(File file, String fileName, String program, Map<String, Object> initialData)
			throws IOException {

		this.file = file;
		this.fileName = fileName;
		this.program = program;
		this.initialData = initialData;
		defValue.put("$fileName", fileName);
		reader();
		if (verifier() == -1) {
			stop = 1;
		}

	}

	/**
	 * Metoda koja provjerava je li sadržaj predanog fajla odgovara programu svemu što se od njega traži.
	 * 
	 * @throws IOException
	 */
	private static int verifier() throws IOException {

		BufferedReader br = new BufferedReader(new StringReader(program));

		while (true) {

			String line = br.readLine();

			/*
			 * Ako je učitani redak prazan ili ako se predhodno pojavaio terminate prekini izvođenje
			 */
			if (line == null || stop == 1) {
				break;
			}

			String instr;
			String arguments;

			try {
				if (line.indexOf('#') != -1) {
					instr = line.substring(0, line.indexOf('#')).substring(0, line.indexOf(" "));
					arguments = line.substring(0, line.indexOf('#')).substring(line.indexOf(" ") + 1,
							line.length());

				}
				else {
					instr = line.substring(0, line.indexOf(" "));
					arguments = line.substring(line.indexOf(" ") + 1, line.length());

				}
			} catch (Throwable e) {
				instr = line;
				arguments = "";
			}

			/*
			 * Odredi koja se instrukcija izvodi
			 */
			switch (instr) {
				case "exists":
					InstrExists instrExists = new InstrExists();
					/*
					 * Ako se u argumentima pojavi samo { to znači da sadrži i još jedan blok unutar tih
					 * zagrada pa njega pročitaj ako se vrati false
					 */
					if (arguments.contains("{") && !arguments.contains("}")) {

						if (!instrExists.execute(arguments, defValue, errors, initialData, content)) {
							readParentheses(br);
						}

						/*
						 * Ako arrgumenti sadrže { a vraćen je true, samo prođi kroz kod dok se ne zatvori taj
						 * blok jer se ne treba izvršavati
						 */
						else {
							readTrash(br);
						}
					}
					else {
						// Ako ne sadrži zagrade samo nastavi s izvođenjem
						instrExists.execute(arguments, defValue, errors, initialData, content);
					}

					break;

				case "!exists":
					InstrNotExists instrNotExists = new InstrNotExists();
					if (arguments.contains("{") && !arguments.contains("}")) {
						if (instrNotExists.execute(arguments, defValue, errors, initialData, content)) {
							readParentheses(br);
						}
						else {
							readTrash(br);
						}
					}
					instrNotExists.execute(arguments, defValue, errors, initialData, content);
					break;
				case "def":
					InstrDef instrDef = new InstrDef();
					instrDef.execute(arguments, defValue, errors, initialData, content);
					break;

				case "fail":
					InstrFail instrFail = new InstrFail();
					instrFail.execute(arguments, defValue, errors, initialData, content);
					break;

				case "format":
					InstrFormat instrFormat = new InstrFormat();
					if (arguments.contains("{") && !arguments.contains("}")) {
						if (instrFormat.execute(arguments, defValue, errors, initialData, content)) {
							readParentheses(br);
						}
						else {
							readTrash(br);
						}
					}
					else
						instrFormat.execute(arguments, defValue, errors, initialData, content);
					break;

				case "terminate":
					return -1;

				case "filename":
					InstrFileName instrFileName = new InstrFileName();
					instrFileName.execute(arguments, defValue, errors, initialData, content);
					break;
			}
		}
		return 0;

	}

	/**
	 * Metoda koja samo prođe kroz program kako bi se pročitao ono što zapravo ne treba.
	 * 
	 * @param br
	 *            Odakle se čita
	 */
	private static void readTrash(BufferedReader br) {

		/*
		 * Broji koliko je otvorenih zagrada kako bi znao gdje je kraj početnog bloka
		 */
		int brojac = 0;

		while (true) {

			String line;

			try {
				line = br.readLine();
				if (line == null) {
					errors.add("Neispravan raspored vitičastih zagrada");
					break;
				}
				else if (line.contains("{") && !line.contains("}")) {
					brojac++;
				}
				else if (line.contains("}") && !line.contains("{")) {
					if (brojac == 0) {
						break;
					}
					brojac--;
				}
			} catch (IOException e) {
				System.out.println("Dogodila se pogreška u čitanju!");
			}
		}

	}

	/**
	 * Metoda koja čita zagrada te koja će rekurzivno izvršavati ono što je u njima.
	 * 
	 * @param br
	 *            Odakle se čita
	 * @throws IOException
	 */
	private static void readParentheses(BufferedReader br) throws IOException {

		StringBuilder builder = new StringBuilder();

		int brojac = 0;
		while (true) {
			String line = br.readLine();
			if (line == null) {
				errors.add("Neispravan raspored vitičastih zagrada");
				break;
			}
			else if (line.contains("{") && !line.contains("}")) {
				brojac++;
			}
			else if (line.equals("}")) {
				if (brojac == 0) {
					new FCFileVerifier(file, fileName, builder.toString(), initialData);
					new FCProgramChecker(builder.toString());
					break;
				}
				else {
					brojac--;
				}
			}
			builder.append(line + "\n");
		}

	}

	private static void reader() {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(file);
			Enumeration zipEntries = zipFile.entries();
			String path;
			while (zipEntries.hasMoreElements()) {
				path = zipEntries.nextElement().toString();
				content.add(path);
			}
			zipFile.close();
		} catch (Throwable e) {
			throw new IllegalArgumentException("Ne postoji datoteka traženog oblika");
		}

	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * @return
	 */
	public Set<String> errors() {
		return this.errors;
	}
}
