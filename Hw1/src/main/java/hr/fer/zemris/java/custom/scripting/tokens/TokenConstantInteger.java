package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za <code>Integer</code> brojeve
 * @author Ivan
 *
 */
public class TokenConstantInteger extends Token{
	int value;
	/**
	 *Kreira novu instancu s <code>Integer</code> tipom
	 * @param value
	 */
	public TokenConstantInteger(int value) {
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
