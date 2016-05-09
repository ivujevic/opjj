/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Razred u kojem se implementira naredba format, koja određuje je li predana datoteka zip ili ne.
 * 
 */
public class InstrFormat implements Instruction {

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {

		String[] array = arguments.split(" ");
		if (array.length != 2 || !array[1].equals("{")) {
			errors.add("Pogreška u naredbi format");
		}

	}

	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {

		String fileName = defValue.get("$fileName");

		if (fileName.substring(fileName.length() - 3, fileName.length()).toLowerCase().equals("zip")) {
			return true;
		}
		errors.add("Krivi format datoteke");
		return false;
	}

}
