/**
 * 
 */
package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred u kojem se zadaje koja datoteka ide na provjeru.
 * 
 */
public class FCDemo {

	/**
	 * Metoda koja se poziva prilikom pokretanja programma.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			throw new IllegalArgumentException("Očekuju se tri parametra!");
		}

		File file = new File(args[0]);
		String program = ucitaj(args[2]);
		String fileName = args[1];
		FCProgramChecker checker = new FCProgramChecker(program);
		if (checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");

			for (String error : checker.errors()) {
				System.out.println(error);
			}

			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}
		Map<String, Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0012345678");
		initialData.put("lastName", "Perić");
		initialData.put("firstName", "Pero");

		FCFileVerifier verifier = new FCFileVerifier(file, fileName, program, initialData);

		if (!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		}
		else {
			System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for (String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}

	/**
	 * Metoda koja čita program iz datoteke.
	 * 
	 * @param programName
	 *            Ime datoteke u koju je program zapisan.
	 * @return String u kojem se nalazi zapisan program.
	 * @throws IOException
	 */
	private static String ucitaj(String programName) throws IOException {

		StringBuilder builder = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(
				new FileInputStream(programName)), StandardCharsets.UTF_8));

		while (true) {
			String redak = br.readLine();
			if (redak == null) {
				break;
			}
			redak = redak.trim();
			// if (redak.startsWith("#") || redak.isEmpty()) {
			// continue;
			// }
			builder.append(redak + "\n");
		}
		br.close();
		return builder.toString();
	}
}