/**
 * 
 */
package hr.fer.zemris.java.tecaj_3;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Razred koji ispisuje elemente bez ponavljanja, ulaz pretvara u skup. Koristi se kolekcija SET.
 * 
 * @author Ivan
 * 
 */
public class BezDuplikata {

	/**
	 * Metoda koja prima paramete iz komandne linije i ispisuje bez ponavljanja.
	 * 
	 * @param args
	 *            Elementi koje treba ispisati bez ponavljanja
	 */
	public static void main(String[] args) {

		/**
		 * HashSet dodaje elemenete u set primjenjujući pravila hash funkcije, elementi nisu sortirani.
		 */

		System.out.println("Preko HashSet-a:");
		ispisiSkup(ukloniDuplikate1(args));
		System.out.println();

		/**
		 * TreeSet dodaje elemente kao u stablo, elementi su sortirani.
		 */
		System.out.println("Preko TreeSet-a:");
		ispisiSkup(ukloniDuplikate2(args));
		System.out.println();

		/**
		 * Kao i HashSet dodaje elemente ali s tom razlikom da pamti kojim su redom elementi dodavani u set.
		 */
		System.out.println("Preko LinkedHashSet-a:");
		ispisiSkup(ukloniDuplikate3(args));
		System.out.println();

	}

	/**
	 * @param args
	 * @return
	 */
	private static Collection<String> ukloniDuplikate3(String[] args) {

		Set<String> set = new LinkedHashSet<String>();
		for (String element : args) {
			set.add(element);
		}
		return set;
	}

	/**
	 * @param ukloniDuplikate1
	 */
	private static void ispisiSkup(Collection<String> col) {

		for (String element : col) {
			System.out.println(element);
		}

	}

	/**
	 * @param args
	 * @return
	 */
	private static Collection<String> ukloniDuplikate2(String[] array) {

		Set<String> set = new TreeSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return set;
	}

	/**
	 * @param args
	 * @return
	 */
	private static Collection<String> ukloniDuplikate1(String[] array) {

		Set<String> set = new HashSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return set;
	}

}
