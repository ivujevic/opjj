/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Razred u kojem se nalazi metoda koja će pozvat ispisivanje programa koristeći Visitor Patern.
 * 
 * @author Ivan
 * 
 */
public class TreeWriter {

	/**
	 * Metoda koja prima kao argument ime datoteke koju treba čitati
	 * 
	 * @param args
	 *            Datoteka koja se čita
	 * @throws IOException
	 *             Iznimka koja se dogodi ako je greška prilikom rada s datotekom.
	 * @throws SmartScriptParserException
	 *             Iznimka koja se događa tijekom parsiranja ulaznog niza.
	 */
	public static void main(final String[] args) throws IOException, SmartScriptParserException {
		if (args.length != 1) {
			System.err.println("Potreban je jedan parametar!");
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
				StandardCharsets.UTF_8));
		StringBuilder builder = new StringBuilder();
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			builder.append(line).append("\n");
		}

		SmartScriptParser p = new SmartScriptParser(builder.toString());
		WriteVisitor visitor = new WriteVisitor();
		p.getDocumentNode().accept(visitor);
		System.out.println(visitor.getText());
	}
}
