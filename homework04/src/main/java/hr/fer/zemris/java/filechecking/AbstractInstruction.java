/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Razred koji implementira sučelje Instruction.
 * 
 */
public abstract class AbstractInstruction implements Instruction {

	/**
	 * Metoda u kojoj se provjerava ispravnost napisanog programa.
	 */
	public abstract void check(String arguments, Map<String, String> defValue, Set<String> errors);

	/**
	 * Metoda u kojoj se izvodi program, odnosno provjerava se je li predani zip sadrži sve potrebno.
	 */
	public abstract boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content);

	/**
	 * Metoda u kojoj se u pathu zamjenjuje ime varijable s njenim sadržajem.
	 * 
	 * @param variable
	 *            Ime varijable
	 * @param path
	 *            Staza u kojoj se varijabla nalazi.
	 * @param defValue
	 *            Kolekcija u koju se spremaju sadržaji varijabli
	 * @return
	 */
	public String changeVariable(String variable, StringBuilder path, Map<String, String> defValue) {

		path.insert(path.indexOf("$"), defValue.get(variable));
		path.delete(path.indexOf("$"), path.indexOf("}") + 1);
		if (path.indexOf(":") != -1) {
			path.replace(path.indexOf(":"), path.indexOf(":") + 1, "/");
		}
		return path.toString();
	}

}
