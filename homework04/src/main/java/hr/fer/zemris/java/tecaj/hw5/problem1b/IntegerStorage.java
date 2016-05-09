/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Objekt IntegerStorage
 * 
 */
public class IntegerStorage {

	private int value;
	List<IntegerStorageObserver> lista;

	Comparator<IntegerStorageObserver> komparator = new Comparator<IntegerStorageObserver>() {

		@Override
		public int compare(IntegerStorageObserver o1, IntegerStorageObserver o2) {
			return o1.toString().compareTo(o2.toString());
		}
	};

	/**
	 * Konstruktor koji postavlja početnu vrijednost
	 * 
	 * @param initialValue
	 *            Početna vrijednost
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		lista = new ArrayList<IntegerStorageObserver>();

	}

	/**
	 * Metoda koja dodaje observere
	 * 
	 * @param observers
	 *            Observeri koji se dodaju
	 */
	public void addObserver(IntegerStorageObserver... observers) {

		for (IntegerStorageObserver o : observers) {
			lista.add(o);
		}
	}

	/**
	 * Metoda koja briše dane observere
	 * 
	 * @param observers
	 *            Observeri koji se brišu
	 */
	public void removeObserver(IntegerStorageObserver... observers) {

		for (IntegerStorageObserver o : observers) {

			if (lista.contains(o)) {
				lista.remove(o);
			}
		}
	}

	/**
	 * Metoda koja briše observer.
	 */
	public void clearObserver() {
		lista.removeAll(lista);
	}

	/**
	 * Metoda koja vraća vrijednost iz observera
	 * 
	 * @return Vrijednost koja se vraća
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Metoda koja postavlja vrijednost te obavještava da je došlo do promjene ako observer postoji
	 * 
	 * @param value
	 *            Vrijednost koja se upisuje
	 */
	public void setValue(int value) {
		if (this.value != value) {

			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			if (lista.size() != 0) {

				for (IntegerStorageObserver o : lista) {
					if (o != null) {
						o.valueChanged(change);
					}
				}
			}
		}
	}

}
