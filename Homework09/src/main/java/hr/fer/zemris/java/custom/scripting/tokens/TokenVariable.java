package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Klasa za varijable
 * 
 * @author Ivan
 * 
 */
public class TokenVariable extends Token {

	String name;

	/**
	 * Kreira novu instancu s imenom varijable
	 * 
	 * @param name
	 *            Ime varijable
	 */
	public TokenVariable(final String name) {
		this.name = name;
	}

	@Override
	public final String asText() {
		return this.name;
	}

	/**
	 * Vraca ime
	 * 
	 * @return
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TokenVariable other = (TokenVariable) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
