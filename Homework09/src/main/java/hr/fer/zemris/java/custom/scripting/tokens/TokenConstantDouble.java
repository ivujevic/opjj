package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za <code>Double</code> tip podataka
 * 
 */
public class TokenConstantDouble extends Token {
	double value;

	/**
	 * Kreira novu instancu s <code>Double</code> vrijednost
	 * 
	 * @param value
	 */
	public TokenConstantDouble(final double value) {
		this.value = value;
	}

	public final double getValue() {
		return value;
	}

	@Override
	public final String asText() {
		return Double.toString(value);
	}
}
