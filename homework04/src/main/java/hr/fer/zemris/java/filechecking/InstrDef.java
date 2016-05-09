/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Razred u kojem se implementira instrukcija Def. Instrukcija postavlja sadržaj varijabli.
 * 
 * @author Ivan
 * 
 */
public class InstrDef extends AbstractInstruction {

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {

		String[] arrayelements = arguments.trim().split("\\s+");

		if (arrayelements.length != 2) {
			errors.add("Krivi poziv naredbe def");
			return;
		}
		StringBuilder path = new StringBuilder();
		path.append(arrayelements[1]);

		path.delete(0, 1);
		path.delete(path.length() - 1, path.length());

		int p = arrayelements[1].indexOf("{");
		int k = arrayelements[1].indexOf("}");

		/*
		 * Ako argument sadrži vitičaste zagrade znači da u njemu postoji varijabla koju treba promijeniti.
		 * Provjeri je li navedena varijabla u argumentu ispravna i je li predhono inicijalizirana i ako je
		 * postavi takav argumenti inače dojavi pogrešku.
		 */
		if (p != -1 && k != -1) {

			String variable = arrayelements[1].substring(p + 1, k);

			if (variable.trim().indexOf(' ') != -1) {
				errors.add("Varijabla ne smije sadržavati praznine");
				return;
			}

			if (!defValue.containsKey(variable)) {
				errors.add("Ne postoji definirana varijabla " + variable);
			}

			else {
				changeVariable(variable, path, defValue);
				defValue.put(arrayelements[0], path.toString());
			}
		}
		else {
			defValue.put(arrayelements[0], path.toString());
		}
	}

	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {

		String[] arrayelements = arguments.trim().split("\\s+");

		if (arrayelements.length != 2) {
			errors.add("Krivi poziv naredbe def");
			return false;
		}

		StringBuilder path = new StringBuilder();
		path.append(arrayelements[1]);

		path.delete(0, 1);
		path.delete(path.length() - 1, path.length());

		int p = arrayelements[1].indexOf("{");
		int k = arrayelements[1].indexOf("}");

		if (p != -1 && k != -1) {

			String variable = arrayelements[1].substring(p + 1, k);

			if (!defValue.containsKey(variable)) {
				errors.add("Ne postoji definirana varijabla " + variable);
			}
			else {
				changeVariable(variable, path, defValue);
				defValue.put(arrayelements[0], path.toString());
			}
		}
		else {
			defValue.put(arrayelements[0], path.toString());
		}
		return false;
	}

}
