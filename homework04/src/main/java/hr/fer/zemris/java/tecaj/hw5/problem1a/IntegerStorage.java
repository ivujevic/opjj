/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1a;

/**
 * Objekt IntegerStorage
 * 
 */
public class IntegerStorage {

	private int value;
	private IntegerStorageObserver observer;

	/**
	 * Konstruktor koji postavlja početnu vrijednost
	 * 
	 * @param initialValue
	 *            Početna vrijednost
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Metoda koja registrira promjenu u observeru.
	 * 
	 * @param observer
	 */
	public void setObserver(IntegerStorageObserver observer) {
		this.observer = observer;
	}

	/**
	 * Metoda koja briše observer.
	 */
	public void clearObserver() {
		this.observer = null;
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
			this.value = value;
			if (observer != null) {
				observer.valueChanged(this);
			}
		}
	}

}
