/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Razred u kojem se implementira labela u kojoj se prikazuju trenutno aktivne boje
 * 
 * @author Ivan
 * 
 */
public class ColorWriter extends JLabel {

	/**
	 * Trenutno aktivna foregorund boja
	 */
	JColorArea foreground;

	/**
	 * Trenutno aktivna background boja
	 */
	JColorArea background;

	/**
	 * Konstruktor koji prima dvije boje
	 * 
	 * @param foreground
	 * @param backgorund
	 */
	public ColorWriter(final JColorArea foreground, final JColorArea backgorund) {
		this.foreground = foreground;
		this.background = backgorund;

		foreground.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(final IColorProvider source, final Color oldColor, final Color newColor) {
				updateText();
			}
		});
	}

	/**
	 * Metoda koja osvježava tekst kada se promjeni boja.
	 * 
	 * @param source
	 * @param oldColor
	 * @param newColor
	 */
	protected final void updateText() {
		Color fColor = foreground.getColor();
		Color bColor = background.getColor();
		setText("Foreground color: (" + fColor.getRed() + "," + fColor.getGreen() + "," + fColor.getBlue()
				+ "), background color: (" + bColor.getRed() + "," + bColor.getGreen() + ","
				+ bColor.getBlue() + ").");
	}

}
