/**
 * 
 */
package hr.fer.zemris.java.strategija;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Razred u kojem se implementira oblikovni obrazac Strategija.
 * 
 * @author Ivan
 * 
 */
public class ObradaDatoteke {

	/**
	 * Razred u kojem se uklanjaju komentari iz linije.
	 * 
	 * @param line
	 *            Linija iz koje je potrebno ukloniti komentare.
	 * @return Linija u kojoj su uklonjeni komentari.
	 */
	private static String ukloniKomentare(String line) {
		int pozicija = line.indexOf('#');
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		pozicija = line.indexOf('%');
		if (pozicija >= 0) {
			line.substring(0, pozicija);
		}
		if (line.contains("REM")) {
			line = "";
		}
		return line;
	}

	/**
	 * Razred u kojem se čita iz datoteke.
	 * 
	 * @param fileName
	 *            Datoteka iz koje se čita
	 * @param obrada
	 *            Generičko sučelje obrada.
	 * @return T učitani sadržaj
	 */
	public static <T> T obradi(String fileName, IObrada<T> obrada) {
		int ocekivaniBrojStupaca = obrada.brojStupaca();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
					StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				line = ukloniKomentare(line);
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] elems = line.split("\t");
				if (elems.length != ocekivaniBrojStupaca) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				obrada.obradiRedak(elems);
			}
			br.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obrada.dohvatiRezultat();
	}

}
