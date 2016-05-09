/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.LinkedList;
import java.util.List;

/**
 * Razred koji prestavlja elemente MultiStacka koji su pohranjeni pod istim ključem
 * 
 */

public class MultistackEntry {

	/**
	 * Struktura koja nam predstavlja stog na koji se pohranjuju vrijednosti.
	 */
	private List<ValueWrapper> stack = new LinkedList<>();

	/**
	 * Prilikom kreiranja novog objekta na stog se dodaje vrijednost.
	 * 
	 * @param valueWrapper
	 *            Vrijednost koja se dodaje prilikom kreiranja objekta
	 */
	public MultistackEntry(ValueWrapper valueWrapper) {
		super();
		stack.add(valueWrapper);
	}

	/**
	 * Dodaje element na vrh stoga.
	 * 
	 * @param element
	 *            Element koji se dodaje na vrh stoga
	 * @return Referenca na stog.
	 */
	public MultistackEntry push(ValueWrapper element) {
		stack.add(element);
		return this;
	}

	/**
	 * Metoda koja određuje veličinu stoga.
	 * 
	 * @return Broj elemenata na stogu
	 */
	public int size() {
		return this.stack.size();
	}

	/**
	 * Metoda koja skida zadnje dodani element na stoga.
	 * 
	 * @return Element koji je na vrhu stoga
	 */
	public ValueWrapper pop() {
		ValueWrapper value;
		value = this.stack.get(this.stack.size() - 1);
		stack.remove(stack.size() - 1);
		return value;
	}

	/**
	 * Metoda koja vraća element koji je na vrhu stoga, ali ga zadržava na stogu.
	 * 
	 * @return Element koji je na vrhu stoga.
	 */
	public ValueWrapper peek() {
		return this.stack.get(this.stack.size() - 1);
	}

}
