/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1b;

public class ObserverExample {

	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);

		SquareValue value = new SquareValue();
		istorage.addObserver(value, new ChangeCounter());
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(value);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}