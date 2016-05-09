/**
 * 
 */
package hr.fer.zemris.java.strategija;

/**
 * Sučelje IObrada koje sadrži metode brojStupaca, obradiRedak, dohvatiRezultat.
 * 
 * @author Ivan
 * @param <T>
 *            Generički parametar
 */
public interface IObrada<T> {
	int brojStupaca();

	void obradiRedak(String[] elems);

	T dohvatiRezultat();
}
