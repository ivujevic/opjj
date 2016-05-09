package hr.fer.zemris.java.tecaj_3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Razred u kojem se ispisuje elementi koje se nalaze unutar jedna, a ne nalaze u drugoj datoteci.
 */
public class Names {

	/**
	 * Metoda koja prima imena dviju datoteka iz komandne linije i ispisuje elemente koji se nalaze u prvoj, a
	 * ne nalaze u drugoj datoteci.
	 * 
	 * @param args
	 *            Imena datoteka
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader f1 = new BufferedReader(new InputStreamReader(new BufferedInputStream(
				new FileInputStream(args[0])), "UTF-8"));

		BufferedReader f2 = new BufferedReader(new InputStreamReader(new BufferedInputStream(
				new FileInputStream(args[1])), "UTF-8"));

		Set<String> set1 = new LinkedHashSet<String>();
		while (true) {
			String input = f1.readLine();

			if (input == null) {
				break;
			}

			input = input.trim();
			if (input.isEmpty()) {
				continue;
			}
			set1.add(input);
		}
		f1.close();

		Set<String> set2 = new LinkedHashSet<>();
		while (true) {
			String input = f2.readLine();

			if (input == null) {
				break;
			}
			input = input.trim();

			if (input.isEmpty()) {
				continue;
			}
			set2.add(input);
		}
		f2.close();

		writeNames(set1, set2);
	}

	/**
	 * Metoda koja ispisuje imena tako da provjerava nalazi li se elementi iz jednog skupa u drugom skupu i
	 * ako se ne nalazi ispisuje ga.
	 * 
	 * @param set1
	 * @param set12
	 */
	private static void writeNames(Set<String> set1, Set<String> set2) {
		for (String element : set1) {
			if (!set2.contains(element)) {
				System.out.println(element);
			}
		}

	}

}
