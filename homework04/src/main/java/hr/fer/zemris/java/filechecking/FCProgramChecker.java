/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Razred u kojem se provjerava ispravnost napisanog programa-
 * 
 */
public class FCProgramChecker {

	/**
	 * Program koji je napisan u datoteci
	 */
	String program;

	/**
	 * Set u koji se pohranjuju nastale pogreške
	 */
	private static Set<String> errors = new LinkedHashSet<>();

	/**
	 * Mapa u koju se pohranjuje vrijednosti varijabli. Ključ je ime varijable.
	 */
	private static Map<String, String> defValue = new LinkedHashMap<>();

	public FCProgramChecker(String program) throws IOException {
		super();
		this.program = program;
		checkProgram();
	}

	/**
	 * Metoda koja provjerava program, podjeli ga na linije te provjerava liniju
	 * 
	 * @throws IOException
	 */
	public void checkProgram() throws IOException {

		BufferedReader br = new BufferedReader(new StringReader(this.program));

		while (true) {
			String line = br.readLine();
			if (line == null) {
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
			switch (instr) {
				case "exists":
					if (arguments.contains("{") && !arguments.contains("}")) {
						readInParentheses(br);
					}
					InstrExists instrExists = new InstrExists();
					instrExists.check(arguments, defValue, errors);
					break;

				case "!exists":
					if (arguments.contains("{") && !arguments.contains("}")) {
						readInParentheses(br);
					}
					InstrExists nIntsrExists = new InstrExists();
					nIntsrExists.check(arguments, defValue, errors);
					break;

				case "format":
					if (arguments.contains("{") && !arguments.contains("}")) {
						readInParentheses(br);
					}
					InstrFormat instrFormat = new InstrFormat();
					instrFormat.check(arguments, defValue, errors);
					break;

				case "fail":
					if(line.indexOf('@') !=-1) {
				
						arguments = line.substring(line.indexOf("@"));
					}

					InstrFail instrFail = new InstrFail();
					instrFail.check(arguments, defValue, errors);
					break;

				case "def":
					InstrDef instrDef = new InstrDef();
					instrDef.check(arguments, defValue, errors);
					break;

				case "terminate":
					InstrTerminate instrTerminate = new InstrTerminate();
					instrTerminate.check(arguments, defValue, errors);
					break;

				case "filename":
					InstrFileName instrFilename = new InstrFileName();
					instrFilename.check(arguments, defValue, errors);
					break;
			}
		}
	}

	/**
	 * Metoda koja čita unutar vitičastih zagrada kako bi se ostavarila rekurzivna provjera
	 * 
	 * @param br
	 *            Odakle se čita
	 * @throws IOException
	 */
	private void readInParentheses(BufferedReader br) throws IOException {
		StringBuilder builder = new StringBuilder();
		// Broji koliko je puta učitana zagrada { kako bi znao gdje je kraj početnog bloka.
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

	/**
	 * Metoda koja govori je li program sadrži greške ili ne.
	 * 
	 * @return <code>true</code> ako program sadrži greške, inače vraća <code>false</code>
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Vraća Set pronađenih pogrešaka
	 * 
	 * @return Set grešaka
	 */
	public Set<String> errors() {
		return this.errors;
	}
}
