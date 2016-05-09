package hr.fer.zemris.java.hw1;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String 	docBody="This is sample text.\r\n" +
				"[$ FOR i 1 10 1 $] " +
				"\r\nThis is [$= i $]-th time this message is generated.\r\n" +
				"[$END$]\r\n" +
				"[$FOR i 0 10 2 $]" +
				"\r\nsin([$=i$]^2) = [$= i i * @sin \"0.000\" @decfmt $]\r\n" +
				"[$END$]";
		SmartScriptParser parser = null;
		try {
			parser =new SmartScriptParser(docBody);
		}catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);	
		}
	private static String createOriginalDocumentBody(Node node) {
		String body = node.asText();
		for(int i = 0; i < node.numberOfChildren(); i++) {
			body += createOriginalDocumentBody(node.getChild(i));
		}

		// Add END tag at the end if it's non-empty tag (ForLoopNode)
		if(node instanceof ForLoopNode)
			body += "[$END$]";

		return body;
	}
	}
