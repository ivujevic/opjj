/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

/**
 * @author Ivan
 * 
 */
public interface ILocalizationProvider {

	public void addLocalizationListener(ILocalizationListener l);

	public void removeLocalizationListener(ILocalizationListener l);

	public String getString(String key);
}
