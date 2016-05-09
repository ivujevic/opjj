/**
 * 
 */
package hr.fer.zemris.java.hw08.jnotepadpp.translator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * 
 * @author Ivan
 * 
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Konstruktor
	 * 
	 * @param provider
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
