/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1b;

/**
 * @author Ivan
 * 
 */
public class ChangeCounter implements IntegerStorageObserver {

	private int numberOfTimes = 0;

	@Override
	public void valueChanged(IntegerStorageChange change) {
		numberOfTimes++;
		System.out.println("Broj promjena vrijednosti je " + numberOfTimes);

	}

}
