/**
 * Paket u kojem se nalaze klase koje su pisane u domaćoj zadaći 3
 */

package hr.fer.zemris.java.tecaj_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Razred koji od danih elemenata ispisuje samo one koji su za 20% veći od prosjeka.
 * 
 */
public class AboveAverage {

	/**
	 * Postotak iznad kojeg se ispisuju brojevi
	 */
	private final static double PERCENTAGE = 1.2;

	/**
	 * 
	 * Metoda koja čita decimalne brojeve dok se ne unese <code>quit</code>, u jednu listu sprema elemente
	 * koje treba izbaciti te na kraju ispisuje elementa koji se ne nalaze u toj listi.
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in, "UTF-8");
		List<Double> list = new ArrayList<>();
		int numOfLoaded = 0;
		double sumOfLoaded = 0;

		while (true) {
			String input = sc.nextLine();

			if (input.equals("quit")) {
				break;
			}

			numOfLoaded++;
			try {
				sumOfLoaded += Double.parseDouble(input);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Trebate unositi samo brojeve");
			}
			list.add(Double.parseDouble(input));
		}
		sc.close();

		double average = sumOfLoaded / numOfLoaded;

		List<Double> listOfOutElements = new ArrayList<>();
		for (Double element : list) {
			if (element <= PERCENTAGE * average) {
				listOfOutElements.add(element);
			}
		}

		list.removeAll(listOfOutElements);
		Collections.sort(list);

		for (Double element : list) {
			System.out.println(element);
		}
	}

}
