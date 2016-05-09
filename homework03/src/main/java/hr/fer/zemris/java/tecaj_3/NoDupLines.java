/**
 * 
 */
package hr.fer.zemris.java.tecaj_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Program koji prima polje stringova i ispisuje sve elemente obrnutim poretkom izbacujući duplikate.
 * 
 */
public class NoDupLines {

	/**
	 * Metoda koja čita redak po redak i sprema ih u polje stringova.
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in, "UTF-8");
		StringBuilder builder = new StringBuilder();
		while (true) {
			String input = sc.nextLine();
			if (input.isEmpty()) {
				break;
			}

			builder.append(input + " ");

		}
		sc.close();

		String[] arrayOfStrings = builder.toString().split(" ");
		ispisi(arrayOfStrings);

	}

	/**
	 * Metoda koja dobiva elemente, dodaje ih u set kako bi se dobio skup tih elemenata te taj set pretvara u
	 * listu da je te ispisuje elemente obrnutim poretkom.
	 * 
	 * Složenost je O(n)
	 * 
	 * @param poljeStringova
	 *            Elementi koji se žele ispisati obrnutim poretkom.
	 */
	private static void ispisi(String[] arrayOfStrings) {

		Set<String> set = new LinkedHashSet<>();

		for (String element : arrayOfStrings) {
			set.add(element);
		}

		List<String> list = new ArrayList<String>(set);
		Collections.reverse(list);

		for (String element : list) {
			System.out.println(element);
		}

	}

}
