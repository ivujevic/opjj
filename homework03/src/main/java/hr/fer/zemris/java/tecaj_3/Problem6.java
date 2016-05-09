/**
 * 
 */
package hr.fer.zemris.java.tecaj_3;

import java.util.HashMap;
import java.util.Map;

/**
 * Kopiran primjer sa slajda 41. prezentacija 5
 * 
 * @author Ivan
 * 
 */
public class Problem6 {

	public static void main(String[] args) {

		Map<String, Integer> ocjena = new HashMap<String, Integer>();
		ocjena.put("Ana", Integer.valueOf(5));
		ocjena.put("Ivo", Integer.valueOf(4));
		ocjena.put("Ivana", Integer.valueOf(5));

		System.out.println("Ivana ima ocjenu: " + ocjena.get("Ivana"));

	}

}
