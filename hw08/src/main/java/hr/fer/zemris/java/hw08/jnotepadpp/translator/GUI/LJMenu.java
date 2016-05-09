/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator.GUI;

import hr.fer.zemris.java.hw08.jnotepadpp.translator.ILocalizationListener;
import hr.fer.zemris.java.hw08.jnotepadpp.translator.ILocalizationProvider;

import javax.swing.JMenu;

/**
 * @author Ivan
 * 
 */
public class LJMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String key;
	ILocalizationProvider lp;

	ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			upadte();

		}
	};

	/**
	 * 
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		lp.addLocalizationListener(listener);
		upadte();
	}

	/**
	 * 
	 */
	protected void upadte() {
		this.setText(lp.getString(key));

	}
}
