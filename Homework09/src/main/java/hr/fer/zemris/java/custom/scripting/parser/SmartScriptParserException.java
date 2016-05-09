package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Ako ulazni string ne moze biti parsiran
 * 
 */
public class SmartScriptParserException extends Exception {

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {
		super();
	}

	public SmartScriptParserException(final String message) {
		super(message);
	}

	public SmartScriptParserException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
