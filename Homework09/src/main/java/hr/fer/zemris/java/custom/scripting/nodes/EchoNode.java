package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Klasa koja se koristi za EchoNode
 * 
 * 
 */
public class EchoNode extends Node {

	Token[] tokens;

	/**
	 * Kreira EchoNode sa grupom tokena
	 * 
	 * @param tokens
	 *            Parametri ovog Node-a
	 */
	public EchoNode(Token[] tokens) {
		this.tokens = tokens;
	}

	/**
	 * Vraca sve tokene
	 * 
	 * @return
	 */
	public Token[] getTokens() {
		return tokens;
	}

	/**
	 * Vraca string kakav izgleda u ulaznom stringu
	 */
	public String asText() {
		String izlaz = "[$=";
		for (int i = 0; i < tokens.length; i++) {
			izlaz += tokens[i].asText() + " ";
		}
		izlaz += "$]";
		return izlaz;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
