package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za funkcije
 *
 */
public class TokenFunction extends Token{
	String name;
	
	/**
	 * Kreira novu instancu za funkcije
	 * @param value
	 */
	public TokenFunction(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return "@" + name;
	}
}
