/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Razred u kojem se implementiraju metode potrebne za obilazak generiranog stabla koristeći Visitor Patern.
 * 
 * @author Ivan
 * 
 */
public class WriteVisitor implements INodeVisitor {

	/**
	 * Tekst koji se postepeno gradi sadržajem stabla.
	 */
	public static String text="";

	@Override
	public final void visitTextNode(final TextNode node) {
		text += node.asText();
		int n = node.numberOfChildren();
		for (int i = 0; i < n; i++) {
			node.getChild(i).accept(this);
		}
	}

	@Override
	public final void visitForLoopNode(final ForLoopNode node) {
		text += node.asText();
		int n = node.numberOfChildren();
		for (int i = 0; i < n; i++) {
			node.getChild(i).accept(this);
		}
	}

	@Override
	public final void visitEchoNode(final EchoNode node) {
		text += node.asText();
		int n = node.numberOfChildren();
		for (int i = 0; i < n; i++) {
			node.getChild(i).accept(this);
		}
	}

	@Override
	public void visitDocumentNode(final DocumentNode node) {
	}

	public final String getText() {
		return text;
	}

}
