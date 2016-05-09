/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

/**
 * @author Ivan
 * 
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	ILocalizationProvider provider;
	ILocalizationListener listener;

	/**
	 * 
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	public void connect() {
		this.connected = true;
		provider.addLocalizationListener(listener);
	}

	public void disconnect() {
		this.connected = false;
		provider.removeLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
