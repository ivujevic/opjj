/**
 * 
 */
package hr.fer.zemris.java.tecaj_3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Program čita imena i ispisuje koliko je puta svako ime upisano
 * 
 */
public class NamesCounter {

	/**
	 * Metoda koja čita imena s tipkovnice dok se ne učita <code>quit</code> i broji koliko je puta koje ime
	 * učitano. Kotisti se mapom gdje je ime ključ, a vrijednost je broj učitavanja
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Map<String, Integer> map = new HashMap<>();

		Scanner sc = new Scanner(System.in, "UTF-8");

		while (true) {
			String name = sc.nextLine();

			if (name.equals("quit")) {
				break;
			}

			if (!map.containsKey(name)) {
				map.put(name, 1);
			}
			else {
				map.put(name, map.get(name) + 1);
			}
		}
		sc.close();

		Iterator<Map.Entry<String, Integer>> mapIterator = map.entrySet().iterator();

		while (mapIterator.hasNext()) {
			Map.Entry<String, Integer> element = mapIterator.next();
			System.out.println(element.getKey() + "  " + element.getValue());
		}

	}

}
