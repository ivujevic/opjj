/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1b;

/**
 * Metoda koja se poziva prilikom promjene vrijednost i ispisuje kvadrat unesene vrijednosti.
 * 
 * @author Ivan
 * 
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange change) {

		int value = change.getCurrentValue();
		System.out.println("Primljena je nova vrijednost: " + value + " kvadrat je " + value * value);

	}

}
