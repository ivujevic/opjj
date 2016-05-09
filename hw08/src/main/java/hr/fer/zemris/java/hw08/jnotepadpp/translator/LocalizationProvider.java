/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Ivan
 * 
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();

	private LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.jnotepadpp.translator.prijevodi",
				locale);
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.jnotepadpp.translator.prijevodi",
				locale);
		fire();
	}

}
