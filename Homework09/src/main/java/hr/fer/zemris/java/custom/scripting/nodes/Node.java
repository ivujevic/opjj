package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

public abstract class Node {
	ArrayBackedIndexedCollection children;

	public abstract void accept(INodeVisitor visitor);

	/**
	 * Dodaje dijete u Node
	 * 
	 * @param child
	 *            Dijete koje se dodaje
	 */
	public final void addChildNode(final Node child) {
		if (children == null) {
			children = new ArrayBackedIndexedCollection();
		}
		children.add(child);
	}

	/**
	 * 
	 * @return Broj djece
	 */
	public final int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * Vraca dijete s pozicije <code>index</code>
	 * 
	 * @param index
	 *            Pozicija s koje se vraca dijete
	 * @return Dijete na zadanoj poziciji
	 */
	public final Node getChild(final int index) {
		return (Node) children.get(index);
	}

	/**
	 * 
	 * @return String kakav bi trebao izgledati u ulaznom stringu
	 */
	public String asText() {
		return "";
	}
}
