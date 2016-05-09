/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * @author Ivan
 * 
 */
public class ValueWrapper {

	/**
	 * Operacija zbrajanja.
	 */
	private final static int OP_INCREMENT = 0;

	/**
	 * Operacija oduzimanja.
	 */
	private final static int OP_DECREMENT = 1;

	/**
	 * Operacija množenja.
	 */
	private final int OP_MULTIPLY = 2;

	/**
	 * Operacija dijeljenja.
	 */
	private final int OP_DIVIDE = 3;

	private Object object;

	public ValueWrapper(Object object) {

		if (object instanceof String) {

			if (isInteger(object)) {
				this.object = new Integer(Integer.parseInt(object.toString()));
			}
			else if (isDouble(object)) {
				this.object = new Double(Double.parseDouble(object.toString()));
			}
		}
		else {
			this.object = object;
		}
	}

	/**
	 * Uvećava vrijednost objekta.
	 * 
	 * @param incValue
	 *            Vrijednost za koju se objekt uvećava.
	 */
	public void increment(Object incValue) {
		operation(incValue, OP_INCREMENT);
	}

	/**
	 * Smanjuje vrijednost objekta.
	 * 
	 * @param decValue
	 *            Vrijednost za koju se objekt smanjuje.
	 */
	public void decrement(Object decValue) {
		operation(decValue, OP_DECREMENT);
	}

	/**
	 * Množi objekt sa zadanom vrijednosti.
	 * 
	 * @param mulValue
	 *            Vrijednost s kojom se objekt množi.
	 */
	public void multiply(Object mulValue) {
		operation(mulValue, OP_MULTIPLY);
	}

	/**
	 * Dijeli objekt sa zadanom vrijednosti.
	 * 
	 * @param divValue
	 *            Vrijednost s kojom se objekt dijeli.
	 */
	public void divide(Object divValue) {
		operation(divValue, OP_DIVIDE);
	}

	/**
	 * Metoda koja radi usporedbu dvaju numeričkih objekata.
	 * 
	 * @param withValue
	 *            Parametar s kojom se određena vrijednost uspoređuje
	 * @return 0 ako su jednaki, -1 ako je vrijdnost manaj od argumenta, 1 ako je vrijednost veća od argumenta
	 */
	public int numCompare(Object withValue) {
		if (object == null) {
			object = new Integer(0);
		}
		if (withValue == null) {
			object = new Integer(0);
		}
		if (isDouble(object) && isDouble(withValue)) {
			return ((Double) Double.parseDouble(object.toString())).compareTo((Double) Double
					.parseDouble(withValue.toString()));
		}
		throw new IllegalArgumentException("Nevaljali parametri");
	}

	public Object getValue() {
		return object;
	}

	/**
	 * Metoda koja prima vrijednost i vrstu operacije koja se izvodi.
	 * 
	 * @param value
	 *            Uz object drugi operand nad kojima se izvodi operacija
	 * @param operation
	 *            Operacija koja se izvodi
	 */
	private void operation(Object valueOfObject, int operation) {

		Object value = valueOfObject;
		if (value == null) {
			value = new Integer(0);
		}

		if (isInteger(value) && isInteger(object)) {
			object = new Integer((int) execute(Double.parseDouble(object.toString()),
					Double.parseDouble(value.toString()), operation));
		}
		else if ((isDouble(value) || isInteger(value)) && (isDouble(object) || isInteger(object))) {
			object = new Double(execute(Double.parseDouble(object.toString()),
					Double.parseDouble(value.toString()), operation));
		}

	}

	/**
	 * Metoda koja izvodi zadanu operaciju.
	 * 
	 * @param value1
	 *            Prvi parametar operacije.
	 * @param value2
	 *            Drugi parametar operacije.
	 * @param operation
	 *            Operacija koju je potrebno izvesti.
	 * @return Vrijednost koja se dobije kada se obavi operacija.
	 */
	private double execute(double value1, double value2, int operation) {

		switch (operation) {
			case OP_INCREMENT:
				return value1 + value2;
			case OP_DECREMENT:
				return value1 - value2;
			case OP_MULTIPLY:
				return value1 * value2;
			case OP_DIVIDE:
				return value1 / value2;

		}
		throw new IllegalArgumentException("Pogreska u argumentima");
	}

	/**
	 * Provjerava je li predani objekt Integer
	 * 
	 * @param object2
	 *            Objekt koji se provjerava
	 * @return <code>true</code> ako je objekt Integer, inače <code>false</code>
	 */
	private boolean isInteger(Object object2) {
		try {
			Integer.parseInt(object2.toString());
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * Provjerava je li dani objekt Double.
	 * 
	 * @param object2
	 *            Objekt koji se provjerava.
	 * @return <code>true</code> ako je Double, inače vraća <code>false</code>
	 */
	private boolean isDouble(Object object2) {
		try {
			Double.parseDouble(object2.toString());
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * Postavlja vrijednost objekta.
	 * 
	 * @param object
	 *            Vrijednost na koju se postavlja.
	 */
	public void setValue(Object object) {
		this.object = object;
	}
}
