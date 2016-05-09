package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Klasa za stringove
 *
 */
public class TokenString extends Token{
	String value;
	
	/**
	 * Kreira novu instancu za <code>String</code> vrijednosti
	 * @param value
	 */
	public TokenString(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return '"' + value +'"';
	}
}
