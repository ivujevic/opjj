/**
 * Paket u kojem se nalaze klase koje su pisane u domaćoj zadaći 3
 */
package hr.fer.zemris.java.tecaj_3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generička klasa koja radi s proizvoljnim objektima te vraća median primjenjih vrijednosti.
 * 
 * @param <T>
 *            Objekt s kojim se radi
 */
public class LikeMedian<T extends Comparable<T>> {

	public ArrayList<T> list;

	/**
	 * Konstrutkor koji stvara listu u kojoj su pohranjeni objekti T
	 */
	public LikeMedian() {
		list = new ArrayList<T>();
	}

	/**
	 * Dodaje element u listu i soritra listu.
	 * 
	 * @param object
	 *            Objekt koji se dodaje u listu
	 */
	public void add(T object) {
		list.add(object);
		Collections.sort(list);
	}

	/**
	 * Metoda koja pronalazi i vraća median ulaznih elemenata
	 * 
	 * @return Median
	 */
	public T get() {
		if (list.size() % 2 == 0) {
			return list.get(list.size() / 2 - 1);
		}
		else {
			return list.get(list.size() / 2);
		}
	}

}
