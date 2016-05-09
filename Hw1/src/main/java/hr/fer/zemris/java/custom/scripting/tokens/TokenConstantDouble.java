package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za <code>Double</code> tip podataka
 *
 */
public class TokenConstantDouble extends Token {
	double value;
	/**
	 * Kreira novu instancu s <code>Double</code> vrijednost
	 * @param value 
	 */
	public TokenConstantDouble(double value) {
		this.value=value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
