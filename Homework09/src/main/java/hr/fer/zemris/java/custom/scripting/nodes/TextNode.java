package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Klasa koja se koristi za Text
 * 
 */
public class TextNode extends Node {
	String text;

	/**
	 * Kreira TextNode
	 * 
	 * @param text
	 *            Tekst koji se sprema u TextNode
	 */
	public TextNode(final String text) {
		this.text = text;
	}

	/**
	 * Vraca tekst
	 * 
	 * @return
	 */
	public final String getText() {
		return text;
	}

	/**
	 * Vraca tekst kakav bi trebao izgledati u ulaznom stringu
	 */
	@Override
	public final String asText() {
		return text;
	}

	@Override
	public final void accept(final INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
