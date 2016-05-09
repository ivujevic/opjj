package hr.fer.zemris.java.custom.scripting.tokens;
/**
 * Klasa za operatore 
 *
 */
public class TokenOperator extends Token {
	String symbol;
	
	/**
	 * Kreira novu instancu za operatore
	 * @param symbol Operator
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}
