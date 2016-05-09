/**
 * 
 */
package hr.fer.zemris.java.okvirnaMetoda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Razred u kojem se implementira oblikovni obrazac okvirna metoda
 * 
 * @author Ivan
 * 
 */
public abstract class ObradaDatoteke<T> {

	/**
	 * Putanja do datoteka koju treba obraditi
	 */
	private String datoteka;

	public ObradaDatoteke(String datoteka) {
		super();
		this.datoteka = datoteka;
	}

	/**
	 * Metoda u kojoj se uklanjaju komentari iz učitane linije.
	 * 
	 * @param line
	 *            Linija iz koje se uklanjaju komentari
	 * @return Linija bez komentara
	 */
	private String ukloniKomentare(String line) {
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
	 * Metoda u kojoj se čita iz datoteke.
	 * 
	 * @param fileName
	 *            Datoteka iz koje se čita.
	 * @return T učitani sadržaj
	 */
	public T ucitaj(String fileName) {

		int ocekivaniBrojStupaca = brojStupaca();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
					StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				line = ukloniKomentare(line);
				line = line.trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] elems = line.split("\t");
				if (elems.length != ocekivaniBrojStupaca) {
					System.out.println("Pogrešan zapis");
					continue;
				}
				obradiRedak(elems);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dohvatiRezultat();
	}

	/**
	 * Metoda koja vraća koliko u datoteci treba biti stupaca.
	 * 
	 * @return Broj stupaca
	 */
	abstract protected int brojStupaca();

	/**
	 * Metoda koja vraća učitani sadržaj.
	 * 
	 * @return T učitani sadržaj
	 */
	abstract protected T dohvatiRezultat();

	/**
	 * Metoda koja obrađuje elemente pročitane u jednom redku.
	 * 
	 * @param elems
	 *            Elementi koji su u redku datoteke.
	 */
	abstract protected void obradiRedak(String[] elems);

}
