/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Razred u kojem se implementira naredba Filename
 * 
 */
public class InstrFileName implements Instruction {

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {

		String[] array = arguments.split(" ");
		if (array.length != 1) {
			errors.add("Pogreška u parametru naredbe filename");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.filechecking.Instruction#execute(java.lang.String, java.util.Map,
	 * java.util.Set, java.util.Map, java.util.Set)
	 */
	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {

		StringBuilder builder = new StringBuilder();
		builder.append(arguments);

		if (builder.charAt(0) == 'i') {
			builder.delete(0, 1);
			builder.append(builder.toString().toUpperCase());
			builder.delete(0, builder.length() / 2);
			defValue.put("$fileName", defValue.get("$fileName").toUpperCase());
		}
		builder.delete(0, 1);
		builder.delete(builder.length() - 1, builder.length());
		builder.insert(builder.indexOf("$"), initialData.get("jmbag"));
		builder.delete(builder.indexOf("$"), builder.indexOf("}", builder.indexOf("$")) + 1);

		if (!builder.toString().equals(defValue.get("$fileName"))) {
			errors.add("Krivi naziv imena datoteke!");
		}
		return true;
	}

}
