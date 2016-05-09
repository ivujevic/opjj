/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.problem1b;

public class IntegerStorageChange {

	private IntegerStorage storage;
	private int previousValue;
	private int currentValue;

	public IntegerStorageChange(IntegerStorage storage, int previousValue, int currentValue) {
		super();

		this.storage = storage;
		this.previousValue = previousValue;
		this.currentValue = currentValue;
	}

	public IntegerStorage getStorage() {
		return storage;
	}

	public int getPreviousValue() {
		return previousValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

}
