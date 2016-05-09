/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * @author Ivan
 * 
 */
public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();

	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(final TextNode node) {
			requestContext.write(node.asText());
		}

		@Override
		public void visitForLoopNode(final ForLoopNode node) {
			if (node.getStepExpression() == null) {
				node.setStepExpression(new TokenConstantInteger(1));
			}
			try {
				multistack.push(node.getVariable().asText(),
						new ValueWrapper(Integer.parseInt(node.getStartExpression().asText())));
			} catch (NumberFormatException e) {
				multistack.push(node.getVariable().asText(),
						new ValueWrapper(Double.parseDouble(node.getStartExpression().asText())));
			}
			int n = node.numberOfChildren();

			while (multistack.peek(node.getVariable().asText()).numCompare(
					Double.parseDouble(node.getEndExpression().asText())) != 1) {
				for (int i = 0; i < n; i++) {
					node.getChild(i).accept(visitor);
				}

				try {
					multistack.peek(node.getVariable().asText()).increment(
							Integer.parseInt(node.getStepExpression().asText()));
				} catch (NumberFormatException e) {
					multistack.peek(node.getVariable().asText()).increment(
							Double.parseDouble(node.getStepExpression().asText()));
				}
			}
			multistack.pop(node.getVariable().asText());
		}

		@Override
		public void visitEchoNode(final EchoNode node) {
			Token[] tokens = node.getTokens();
			Stack<Object> stack = new Stack<>();
			for (Token t : tokens) {
				if (t instanceof TokenConstantInteger) {
					stack.push(Integer.valueOf(t.asText()));
				}
				else if (t instanceof TokenConstantDouble) {
					stack.push(Double.valueOf(t.asText()));
				}
				else if (t instanceof TokenString) {
					String text = t.asText();
					// Ukloni početne i završne navodnike
					stack.push(text.substring(1, text.length() - 1));
				}
				else if (t instanceof TokenVariable) {

					stack.push(multistack.peek(t.asText()).getValue());
				}
				else if (t instanceof TokenOperator) {

					double b = (double) Double.parseDouble(String.valueOf(stack.pop()));
					double a = (double) Double.parseDouble(String.valueOf(stack.pop()));
					switch (t.asText()) {
						case "+":
							if (b != (int) b || a != (int) a) {
								stack.push(a + b);
							}
							else {
								stack.push((int) a + (int) b);
							}
							break;
						case "-":
							if (b != (int) b || a != (int) a) {
								stack.push(a - b);
							}
							else {
								stack.push((int) a - (int) b);
							}
							break;
						case "*":
							if (b != (int) b || a != (int) a) {
								stack.push(a * b);
							}
							else {
								stack.push((int) a * (int) b);
							}
							break;
						case "/":
							stack.push(Double.valueOf(String.valueOf(a / b)));
						default:
							break;
					}
				}
				else if (t instanceof TokenFunction) {
					if (t.asText().contains("@sin")) {
						try {
							double x = (double) stack.peek();
							stack.pop();
							stack.push(Double.valueOf(Math.sin(Math.toRadians(x))));
						} catch (ClassCastException e) {
							int x = (int) stack.pop();
							stack.push(Double.valueOf(Math.sin(Math.toRadians(x))));
						}
					}
					else if (t.asText().contains("@decfmt")) {

						Object y = stack.pop();
						double number = (double) Double.parseDouble(String.valueOf(stack.pop()));

						DecimalFormat format = new DecimalFormat((String) y);
						number = Double.parseDouble((format.format(number)).replace(',', '.'));
						stack.push(number);
					}
					else if (t.asText().contains("@dup")) {
						Object x = stack.pop();
						stack.push(x);
						stack.push(x);
					}
					else if (t.asText().contains("@swap")) {
						Object a = stack.pop();
						Object b = stack.pop();
						stack.push(a);
						stack.push(b);
					}
					else if (t.asText().contains("@setMimeType")) {
						Object x = stack.pop();
						requestContext.setMimeType(String.valueOf(x));
					}
					else if (t.asText().contains("@pparamGet")) {
						Object defValue = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getPersistentParameter(String.valueOf(name));
						stack.push(value == null ? defValue : value);
					}
					else if (t.asText().contains("@paramGet")) {
						Object defValue = stack.pop();
						String name = (String) stack.pop();
						String value = requestContext.getParameter(name);
						stack.push(value == null ? defValue : value);
					}
					else if (t.asText().contains("@pparamSet")) {
						String name = String.valueOf(stack.pop());
						String value = String.valueOf(stack.pop());
						requestContext.setPersistentParameter(name, value);
					}
					else if (t.asText().contains("@pparamDel")) {
						String name = String.valueOf(stack.pop());
						requestContext.removePersistenetParameter(name);
					}
					else if (t.asText().contains("@tparamGet")) {
						Object defValue = stack.pop();
						Object name = stack.pop();
						String value = requestContext.getTemporaryParameter(String.valueOf(name));
						stack.push(value == null ? defValue : value);
					}
					else if (t.asText().contains("@tparamSet")) {
						String name = String.valueOf(stack.pop());
						String value = String.valueOf(stack.pop());
						requestContext.setTemporaryParameter(name, value);
					}
					else if (t.asText().contains("@tparamDel")) {
						String name = String.valueOf(stack.pop());
						requestContext.removeTemporaryParameter(name);
					}
				}
			}
			int n = stack.size();
			Stack<Object> tempStack = new Stack<>();
			for (int i = 0; i < n; i++) {
				tempStack.push(stack.pop());
			}
			for (int i = 0; i < n; i++) {
				requestContext.write(tempStack.pop().toString());
			}
		}

		@Override
		public void visitDocumentNode(final DocumentNode node) {
			int n = node.numberOfChildren();
			for (int i = 0; i < n; i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};

	public SmartScriptEngine(final DocumentNode documentNode, final RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	public final void execute() {
		documentNode.accept(visitor);
	}
}
