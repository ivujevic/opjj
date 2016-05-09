/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * @author Ivan
 * 
 */
public interface INodeVisitor {

	/**
	 * Pojećuje TextNode.
	 * 
	 * @param node
	 *            TextNode koji posjećuje
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Posjećuje FoorLoopNode.
	 * 
	 * @param node
	 *            FoorLoopNode koji posjećuje
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Posjećuje EchoNode.
	 * 
	 * @param node
	 *            EchoNode koji posjećuje
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Posjećuje DocumentNode.
	 * 
	 * @param node
	 *            DocumentNode koji posjećuje
	 */
	public void visitDocumentNode(DocumentNode node);
}
