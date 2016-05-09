package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za funkcije
 * 
 */
public class TokenFunction extends Token {
	String name;

	/**
	 * Kreira novu instancu za funkcije
	 * 
	 * @param value
	 */
	public TokenFunction(final String value) {
		this.name = value;
	}

	public final String getName() {
		return name;
	}

	@Override
	public final String asText() {
		return "@" + name;
	}
}
