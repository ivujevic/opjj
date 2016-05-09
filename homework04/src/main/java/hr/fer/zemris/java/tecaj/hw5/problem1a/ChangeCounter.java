/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1a;

/**
 * Metoda koja broji koliko je puta promjenjena vrijednost
 * 
 * @author Ivan
 * 
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Broj puta koliko je promjenjena vrijednost.
	 */
	private int numberOfTimes = 0;

	@Override
	public void valueChanged(IntegerStorage istorage) {

		numberOfTimes++;
		System.out.println("Broj promjena je " + numberOfTimes);
	}

}
