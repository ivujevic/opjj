/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * @author Ivan
 * 
 */
public class InstrExists extends AbstractInstruction {

	/**
	 * Polje u koje se spremaju arguementi
	 */
	private static String[] arrayelements;

	/**
	 * Instrukcija.
	 */
	private static String[] instr;

	/**
	 * Poruka koju je potrebno ispisati ako je ejdan od argumenata ispis.
	 */
	private static String message;

	/**
	 * Builder koji se koristi za lakši rad sa znakovima.
	 */
	private static StringBuilder builder;

	/**
	 * Set u koji se spremaju pronađene pogreške.
	 */
	private static Set<String> errors;

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {

		builder = new StringBuilder();
		sredi(arguments);

		int p = this.builder.indexOf("{");
		int k = this.builder.indexOf("}");

		if (p != -1 && k != -1) {
			String variable = builder.substring(p + 1, k);
			if (!defValue.containsKey(variable)) {
				errors.add("Ne postoji definirana varijabla " + variable);
			}
		}

	}

	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {

		builder = new StringBuilder();
		this.errors = errors;
		sredi(arguments);

		/*
		 * Ako se u pathu nalazi varijabla zamijeni je.
		 */
		int p = this.builder.indexOf("{");
		int k = this.builder.indexOf("}");

		if (arguments.startsWith("d")) {
			builder.append("/");
		}

		String variable;
		String find = builder.toString();

		if (p != -1 && k != -1) {
			variable = builder.substring(p + 1, k);
			find = changeVariable(variable, builder, defValue);
		}

		if (!content.contains(find.replaceAll("\\.(?=.*\\.)", "/"))) {
			errors.add("Ne sadrži putanju " + find);
			if (message != null) {
				System.out.println(message);
			}
			return false;
		}

		return true;
	}

	private void sredi(String arguments) {

		this.arrayelements = arguments.trim().split("@");
		this.instr = arrayelements[0].trim().split("\\s+");
		message = null;
		if (arrayelements.length != 1) {
			if (arrayelements[1].contains("{")) {
				message = arrayelements[1].substring(0, arrayelements[1].length() - 3).substring(1);
			}
			else {
				message = arrayelements[1].substring(1, arrayelements[1].length() - 1);
			}
		}
		
		if (instr.length != 2 && (instr.length == 3 && message == null && !instr[2].equals("{"))) {
			errors.add("Krivi poziv naredbe exists");
			return;
		}
		if (!(instr[0].equals("d") || instr[0].equals("dir") || instr[0].equals("di") || instr[0].equals("f")
				|| instr[0].equals("fi") || instr[0].equals("fil") || instr[0].equals("file"))) {

			errors.add("Pogreska u prvom parametru naredbe exists");
		}
		this.builder.append(instr[1]);
		this.builder.deleteCharAt(0);
		this.builder.deleteCharAt(builder.length() - 1);
	}

}
