/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Razred u kojem se implementira naredba <code>exists</code> koja prekida program.
 * 
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments != null) {
			System.err.println("Ne smiju biti parametri!");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

}
