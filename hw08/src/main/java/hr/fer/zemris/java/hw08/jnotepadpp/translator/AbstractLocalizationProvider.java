/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

import java.util.ArrayList;
import java.util.List;

/**
 * Radi s listenerima
 * 
 * @author Ivan
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Lista listenera
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();

	public AbstractLocalizationProvider() {

	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Obavještava sve listenere
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
