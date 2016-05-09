package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za varijable
 * @author Ivan
 *
 */
public class TokenVariable extends Token {
	
	String name;
	/**
	 * Kreira novu instancu s imenom varijable
	 * @param name Ime varijable
	 */
	public TokenVariable(String name) {
		this.name=name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
	
	/**
	 * Vraca ime
	 * @return
	 */
	public String getName() {
		return name;
	}
	
}
