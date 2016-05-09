package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za <code>Integer</code> brojeve
 * 
 * @author Ivan
 * 
 */
public class TokenConstantInteger extends Token {
	int value;

	/**
	 * Kreira novu instancu s <code>Integer</code> tipom
	 * 
	 * @param value
	 */
	public TokenConstantInteger(final int value) {
		this.value = value;
	}

	public final int getValue() {
		return value;
	}

	@Override
	public final String asText() {
		return Integer.toString(value);
	}
}
