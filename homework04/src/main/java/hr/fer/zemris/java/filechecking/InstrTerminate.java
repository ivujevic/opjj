/**
 * 
 */
package hr.fer.zemris.java.filechecking;

import java.util.Map;
import java.util.Set;

/**
 * Implementacija naredbe terminate koja prekida izvođenje programa.
 * 
 * @author Ivan
 * 
 */
public class InstrTerminate implements Instruction {

	@Override
	public void check(String arguments, Map<String, String> defValue, Set<String> errors) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean execute(String arguments, Map<String, String> defValue, Set<String> errors,
			Map<String, Object> initialData, Set<String> content) {
		return true;
	}

}
