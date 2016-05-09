/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Razred u kojem se implementira naredba <code>charsets</code>.
 * 
 * @author Ivan
 * 
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments != null) {
			System.err.println("Naredba charsets mora biti bez argumenata!");
		}

		Map<String, Charset> charSets = Charset.availableCharsets();

		for (Map.Entry<String, Charset> c : charSets.entrySet()) {
			try {
				out.write(c.getKey());
				out.newLine();
				out.flush();

			} catch (IOException e) {
				System.err.println("Ne mogu pisati na zadani izlaz");
			}
		}

		return ShellStatus.CONTINUE;
	}

}
