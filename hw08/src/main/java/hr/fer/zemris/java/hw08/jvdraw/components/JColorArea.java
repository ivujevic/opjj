/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Razred u kojem se implementira komponenta JColorArea. Ta komponenta nudi mogućnost odabira boje, te
 * obavještava sve slušatelje da je boja promjenjena.
 * 
 * @author Ivan
 * 
 */
public class JColorArea extends JComponent implements IColorProvider {

	private ArrayList<ColorChangeListener> observer = new ArrayList<>();

	Color color;
	Color oldColor;

	public JColorArea(Color color) {
		super();
		this.color = color;

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				showColorChoser();

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();

		Dimension dim = getPreferredSize();
		g.fillRect(ins.left, ins.top, dim.width - 1 - ins.left - ins.right, dim.height - 1 - ins.top
				- ins.bottom);
		this.setSize(this.getPreferredSize());
		this.setForeground(this.color);
		obavijesti();
	}

	/**
	 * Obaviještava slušače da je nastala promjena
	 */
	private void obavijesti() {

		for (ColorChangeListener l : observer) {
			l.newColorSelected(this, oldColor, color);
		}
	}

	/**
	 * Otvori izbornik boja te postavi zadanu boju na boju koju korisnik odabere.
	 */
	protected void showColorChoser() {
		Color color = JColorChooser.showDialog(this, "Choose color", this.color);
		if (color == null) {
			return;
		}
		oldColor = color;
		this.color = color;
	}

	/**
	 * Metoda koja vraća uvijek da je najbolja veličnia 15 X 15
	 */
	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * Metoda koja vraća odabranu boju u ovoj komponenti.
	 * 
	 * @return Odabrana boja.
	 */
	public Color selectedColor() {
		return this.color;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	public void addColorChangeListener(ColorChangeListener l) {
		observer.add(l);
	}

	public void removeColorChangeListener(ColorChangeListener l) {
		observer.remove(l);
	}
}
