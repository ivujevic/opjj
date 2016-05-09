package hr.fer.zemris.java.custom.collections;

/**
 * Aktivira se u slucaju da je stog prazan, a s njega se zeli uzeti element
 *
 */

public class EmptyStackException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyStackException() {
		super();
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
	
	public EmptyStackException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
}
