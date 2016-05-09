/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Metoda koja prima stazu direktorija i kreira je.
 * 
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments.length != 1) {
			System.err.println("Krivi broj parametara");
			return ShellStatus.CONTINUE;
		}
		if (new File(arguments[0]).mkdirs()) {
			try {
				out.write(Symbols.PROMPTSYMBOL);
				out.write("Uspješno kreirano! ");
				out.newLine();
				out.flush();
			} catch (IOException e) {

			}
		}
		else {
			System.err.println("Unijeli ste pogrešnu stazu!");
		}
		return ShellStatus.CONTINUE;
	}

}
