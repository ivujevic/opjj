/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Razred koji implementira funkciju <code>cat</code>. Prihvaća jedan ili dva argumenta.
 * 
 * @author Ivan
 * 
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments == null || arguments.length != 1 && arguments.length != 2) {
			throw new IllegalArgumentException("Unijeli ste krive parametre naredbi cat");
		}

		Charset charset;

		try {
			charset = Charset.forName(arguments[0]);
		} catch (Throwable e) {
			charset = Charset.defaultCharset();
		}

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(
					arguments[0])), charset));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				out.write(line);
				out.newLine();
				out.flush();

			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("Navedena datoteka ne postoji");
		} catch (IOException e) {
			System.err.println("Dogodila se pogreška tijekom čitanja iz datoteke");
		}

		return ShellStatus.CONTINUE;
	}
}
