package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za stringove
 * 
 */
public class TokenString extends Token {
	String value;

	/**
	 * Kreira novu instancu za <code>String</code> vrijednosti
	 * 
	 * @param value
	 */
	public TokenString(final String value) {
		this.value = value;
	}

	public final String getValue() {
		return value;
	}

	@Override
	public final String asText() {
		return '"' + value + '"';
	}
}
