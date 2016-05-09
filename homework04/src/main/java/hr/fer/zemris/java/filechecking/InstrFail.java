/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Razred u kojem se implementira instrukcija Fail.
 * 
 */
public class InstrFail extends AbstractInstruction {

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {
		String[] array = arguments.split("\"");
		if (array.length != 2) {
			errors.add("Pogreška u naredbi Fail");
		}
	}

	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {
		StringBuilder builder = new StringBuilder();

		builder.append(arguments);

		/*
		 * Ako je f=0 tada je tekst koji se odmah ispisuje, inače je tekst koji se dodaje u pogreške
		 */
		int f = 0;

		if (builder.indexOf("@") != -1) {
			f = 1;
		}

		builder.deleteCharAt(builder.length() - 1);
		if (f == 1) {
			errors.add(builder.substring(2));
		}
		else {
			System.out.println(builder.substring(2));
		}
		return false;
	}

}
