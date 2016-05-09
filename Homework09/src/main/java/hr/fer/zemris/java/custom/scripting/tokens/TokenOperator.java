package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za operatore
 * 
 */
public class TokenOperator extends Token {
	String symbol;

	/**
	 * Kreira novu instancu za operatore
	 * 
	 * @param symbol
	 *            Operator
	 */
	public TokenOperator(final String symbol) {
		this.symbol = symbol;
	}

	public final String getSymbol() {
		return symbol;
	}

	@Override
	public final String asText() {
		return symbol;
	}
}
