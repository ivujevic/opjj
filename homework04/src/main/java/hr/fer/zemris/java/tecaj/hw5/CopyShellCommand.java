/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Metoda u kojoj se implementira naredba copy. Naredba prima dva argumenta, prvi argument predstavlja
 * datoteku koja se želi kopirati, a drugi argument predstavlja direktorij u koji se želi kopirati. Ako drugi
 * argument sadrži i ime datoteke tada se datoteka kopira s tim imenom inače se kopira s originalnim imenom.
 * 
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {

		if (arguments.length != 2) {
			System.err.println("Naredba copy prima 2 argumenta");
			return ShellStatus.CONTINUE;
		}

		Path srcFile = Paths.get(arguments[0]);
		Path destFile = Paths.get(arguments[1]);

		if (!Files.exists(srcFile)) {
			System.err.println("Ne postoji datoteka koju želite kopirati!");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(srcFile)) {
			System.err.println("Ne možete kopirati direktorij");
			return ShellStatus.CONTINUE;
		}

		/*
		 * Ako je kao odredište naveden put do datoteke provjeri je li ta datoteka postoji.
		 */
		if (!Files.isDirectory(destFile) && Files.exists(destFile)) {

			try {
				out.write("Datoteka postoji, želite li je zamijeniti ? Odaberite D za da, inače N");
				out.newLine();
				out.flush();

				/*
				 * Odogovor na postavljeno pitanje
				 */
				String answ = in.readLine().toUpperCase();

				if (answ.equals("n")) {

					return ShellStatus.CONTINUE;
				}
			} catch (IOException e) {
				System.err.println("Dogodila se pogreška!");
				return ShellStatus.CONTINUE;
			}

		}

		/*
		 * Ako je odredište direktorij bez imena datoteke tada mu dodaj ime s originalne datoteke i kopiraj
		 */
		if (Files.isDirectory(destFile)) {
			StringBuilder builder = new StringBuilder();
			builder.append(destFile.toString() + "\\" + srcFile.getFileName().toString());
			try {
				Files.copy(srcFile, Paths.get(builder.toString()), StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
				out.write("Kopiranje je uspješno obavljeno!");
				return ShellStatus.CONTINUE;
			} catch (IOException e) {

				System.err.println("Dogodila se pogreška tijekom kopiranja");
				return ShellStatus.CONTINUE;
			}
		}

		try {
			Files.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING,
					StandardCopyOption.COPY_ATTRIBUTES);
			out.write("Kopiranje je uspješno obavljeno!");
		} catch (IOException e) {
			System.err.println("Dogodila se pogreška tijekom kopiranja");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

}
