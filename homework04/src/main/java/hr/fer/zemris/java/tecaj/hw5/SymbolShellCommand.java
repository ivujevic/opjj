/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Razred u kojem se implementira metoda <code>symbol</code> koja mijenja oznaku za određeni simbol.
 * 
 */

public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments.length != 1 && arguments.length != 2) {
			System.err.println("Pogreska u parametrima");
			return ShellStatus.CONTINUE;
		}

		if (arguments[0].equals(Symbols.PROMPTSYMBOL) || arguments[0].equals(Symbols.MORELINESSYMBOL)
				|| arguments[0].equals(Symbols.MULTILINE) || arguments[0].equals("/")) {
			System.err.println("Ne možete promijeniti na navedeni simbol, već se koristi!");
			return ShellStatus.CONTINUE;
		}

		switch (arguments[0]) {
			case "PROMPT":
				if (arguments.length == 2) {
					replace(out, Symbols.PROMPTSYMBOL, arguments[1].charAt(0), "PROMPT");
					break;
				}

				write(out, "PROMPT", Symbols.PROMPTSYMBOL);
				break;

			case "MORELINES":

				if (arguments.length == 2) {
					replace(out, Symbols.MORELINESSYMBOL, arguments[1].charAt(0), "MORELINE");
					break;
				}

				write(out, "MORELINES", Symbols.MORELINESSYMBOL);
				break;
			case "MULTILINE":
				if (arguments.length == 2) {

					replace(out, Symbols.MULTILINE, arguments[1].charAt(0), "MULTILINE");
					break;
				}

				write(out, "MULTILINE", Symbols.MULTILINE);
				break;

		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja ispisuje koji se simbol koristi.
	 * 
	 * @param out
	 *            Odredište gdje se ispisuje
	 * @param name
	 *            Naziv simbola koji se ispisuje
	 * @param symbol
	 *            Simbol koji se ispisuje
	 */
	private void write(BufferedWriter out, String name, char symbol) {

		try {
			out.write("Symbol for " + name + " is '" + symbol + "'");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.println("Dogodila se pogreška!");
		}
	}

	/**
	 * Metoda koja mijenja simbol.
	 * 
	 * @param out
	 *            Odredište gdje se ispisuje
	 * @param symbol
	 *            Simbol koji se zamjenjuje
	 * @param newSymbol
	 *            Novi simbol
	 * @param name
	 *            Ime simbola
	 */
	private void replace(BufferedWriter out, char symbol, char newSymbol, String name) {

		switch (name) {
			case "PROMPT":
				Symbols.PROMPTSYMBOL = newSymbol;
				break;
			case "MORELINE":
				Symbols.MORELINESSYMBOL = newSymbol;
				break;
			case "MULTILINE":
				Symbols.MULTILINE = newSymbol;
				break;
		}

		try {
			out.write("Symbol for " + name + " changed from '" + symbol + "'" + " to '" + newSymbol + "'");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.print("Dogodila se pogreška");
		}

	}
}
