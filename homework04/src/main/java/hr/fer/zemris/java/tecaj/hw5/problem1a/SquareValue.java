/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1a;

/**
 * Metoda koja se poziva prilikom promjene vrijednost i ispisuje kvadrat unesene vrijednosti.
 * 
 * @author Ivan
 * 
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Primljena je nova vrijednost " + value + " kvadrat je " + value * value);

	}

}
