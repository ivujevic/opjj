package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.demo.WriteVisitor;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Klasa koja se koristi za FOR-LOOP
 * 
 */
public class ForLoopNode extends Node {

	TokenVariable variable;
	Token startExpression;
	Token endExpression;
	Token stepExpression;

	/**
	 * 
	 * @param variable
	 *            Varijabla koja se koristi u FOR.
	 * @param startExpression
	 *            Pocetak FOR petlje.
	 * @param endExpression
	 *            Kraj FOR petlje.
	 * @param stepExpression
	 *            Korak FOR petlje.
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression, Token endExpression,
			Token stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * 
	 * @return vraca sadrzaj variable.
	 */
	public TokenVariable getVariable() {
		return variable;
	}

	public void setStepExpression(Token stepExpression) {
		this.stepExpression = stepExpression;
	}

	/**
	 * 
	 * @return Vraca sadrzaj startExpression.
	 */
	public Token getStartExpression() {
		return startExpression;
	}

	/**
	 * 
	 * @return Vraca sadrzaj endExpression.
	 */
	public Token getEndExpression() {
		return endExpression;
	}

	/**
	 * 
	 * @return Vraca sadrzaj stepExpression.
	 */
	public Token getStepExpression() {
		return stepExpression;
	}

	/**
	 * Vraca tekst kakv bi trebao izgledati u ulaznom stringu
	 */

	@Override
	public String asText() {
		String tag = "[$ FOR " + variable.asText() + " " + startExpression.asText() + " "
				+ endExpression.asText();
		if (stepExpression != null) {
			tag = tag + " " + stepExpression.asText();
		}

		return tag + "$]";
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
		// ako si pročitao unutrašnjos FOR petlje dodaj na kraju END
		WriteVisitor.text += "[$END$]";
	}
}
