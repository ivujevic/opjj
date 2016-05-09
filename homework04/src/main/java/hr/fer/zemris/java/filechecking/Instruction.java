/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Sučelje instrukcija.
 * 
 * @author Ivan
 * 
 */
public interface Instruction {

	/**
	 * Metoda koja provjerava ispravnost napisanih naredbi
	 * 
	 * @param arguments
	 * @param defValue
	 * @param errors
	 */
	public void check(String arguments, Map<String, String> defValue, Set<String> errors);

	/**
	 * Metoda koja prima argumente funkcije, set u kojem se nalaze definirane varijable i set u koji upisuju
	 * pogreške
	 * 
	 * @param arguments
	 * @param defValue
	 * @param errors
	 */
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content);

}
