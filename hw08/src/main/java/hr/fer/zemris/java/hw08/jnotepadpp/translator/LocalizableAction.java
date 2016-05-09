/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * @author Ivan
 * 
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String key;

	public LocalizableAction(final String key, final ILocalizationProvider lp) {
		this.key = key;

		String translation = lp.getString(key);
		this.putValue(NAME, translation);

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(NAME, translation);
			}
		});
	}

	public LocalizableAction(final String key, final ILocalizationProvider lp, ImageIcon icon) {
		putValue(Action.SMALL_ICON, icon);
		this.key = key;

		String translation = lp.getString(key);
		this.putValue(NAME, translation);
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(NAME, translation);
			}
		});
	}

	/**
	 * @param string
	 * @param flp
	 * @param imageIcon
	 * @param string2
	 */
	public LocalizableAction(final String key, final ILocalizationProvider lp, ImageIcon icon,
			final String description) {
		putValue(Action.SMALL_ICON, icon);
		this.key = key;

		String translation = lp.getString(key);
		this.putValue(NAME, translation);
		this.putValue(SHORT_DESCRIPTION, lp.getString(description));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(NAME, translation);
				putValue(SHORT_DESCRIPTION, lp.getString(description));
			}
		});
	}

}
