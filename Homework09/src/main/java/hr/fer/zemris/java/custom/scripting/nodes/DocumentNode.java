package hr.fer.zemris.java.custom.scripting.nodes;


public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		int n = this.numberOfChildren();
		for (int i = 0; i < n; i++) {
			this.getChild(i).accept(visitor);
		}
	}

}
