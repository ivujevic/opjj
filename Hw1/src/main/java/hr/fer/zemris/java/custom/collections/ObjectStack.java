package hr.fer.zemris.java.custom.collections;

public class ObjectStack {
	private ArrayBackedIndexedCollection stack;
	/**
	 * Kreira novi stog
	 */
	public ObjectStack() {
		stack= new ArrayBackedIndexedCollection();
		
	}
	
	/**
	 * Provjerava je li stog prazan
	 * @return Ako je prazan vraca <code>true</code>, inace vraca <code>false</code>
	 */
	public boolean isEmpty() {	
		return stack.isEmpty();
	}
	
	/**
	 * Vraca broj elemenata na stogu
	 * @return Velicinu stoga
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Brise sve elemente sa stoga
	 */
	public void clear() {
		stack.clear();
	}
	
	/**
	 * Dodaje <code>value</code> na stog
	 * @param value Element koji se dodaje
	 */
	public void push(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Vrijednost ne moze biti null");
		}
		stack.add(value);
	}
	
	/**
	 * Skida zadnji element pohranjen na stogu i vraca ga
	 * @return Element koji je zadnji pohranjen na stogu
	 */
	public Object pop() {
		if(stack.size() == 0) {
			throw new EmptyStackException("Stog je prazan!!");
		}
		
		Object temp= stack.get(stack.size()-1);
		stack.remove(stack.size()-1);
		return temp;
	}
	
	/**
	 * Vraca element koji je zadnji pohranjen na stogu, ali ga ne brise sa stoga
	 * @return Element koji je zadnji pohranjen na stogu
	 */
	public Object peek() {
		if(stack.size() == 0) {
			throw new EmptyStackException("Stog je prazan!!");
		}
		
		Object temp= stack.get(stack.size()-1);
		return temp;
	}
	
}
