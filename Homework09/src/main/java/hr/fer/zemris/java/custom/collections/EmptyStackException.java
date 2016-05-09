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

	public EmptyStackException(final String message) {
		super(message);
	}

	public EmptyStackException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EmptyStackException(final Throwable cause) {
		super(cause);
	}
}
