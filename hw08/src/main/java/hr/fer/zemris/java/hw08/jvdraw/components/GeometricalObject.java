/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Razred koji predstavlja jedan geometrijski lik.
 * 
 * @author Ivan
 * 
 */
public class GeometricalObject {

	/**
	 * Prva točka
	 */
	public Point firstPoint = null;

	/**
	 * Druga točka
	 */
	public Point secondPoint = null;

	/**
	 * background boja
	 */
	public Color backgroundColor = null;

	/**
	 * foreground boja
	 */
	public Color foregroundColor = null;

	protected final synchronized Color getBackgroundColor() {
		return backgroundColor;
	}

	protected final synchronized void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	protected final synchronized Color getForegroundColor() {
		return foregroundColor;
	}

	protected final synchronized void setForegroundColor(final Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * Konstruktor
	 */
	public GeometricalObject() {

	}

	public final synchronized Point getFirstPoint() {
		return firstPoint;
	}

	public final synchronized void setFirstPoint(final Point firstPoint) {
		this.firstPoint = firstPoint;
	}

	public final synchronized Point getSecondPoint() {
		return secondPoint;
	}

	public final synchronized void setSecondPoint(final Point secondPoint) {
		this.secondPoint = secondPoint;
	}

	/**
	 * Metoda za crtanje koju svi likovi overidaju
	 * 
	 * @param g
	 */
	public void draw(final Graphics g) {
	}

	/**
	 * Metoda koja priprema dialog koji treba prikazati za promjenu svojstava lika.
	 */
	public void showDialog() {
	}

	/**
	 * Mijenja postavke lika
	 * 
	 * @param properties
	 *            Zadane postavke
	 */
	public final void change(final ChangeProperties properties) {
		String firstPoint = properties.getFirstText().getText();
		String[] point = firstPoint.split(",");
		this.setFirstPoint(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));

		this.setForegroundColor(properties.getColor().getColor());
		if (properties.getFilledCircleColor().isVisible()) {
			this.setBackgroundColor(properties.getFilledCircleColor().getColor());
		}

	}

	/**
	 * Razred novih postavki
	 * 
	 * @author Ivan
	 * 
	 */
	public class ChangeProperties {
		JPanel panel = new JPanel();
		JTextField firstText = new JTextField(firstPoint.x + "," + firstPoint.y);
		JTextField secondText = new JTextField(secondPoint.x + "," + secondPoint.y);
		JLabel firstLabel = new JLabel("First point");
		JLabel secondLabel = new JLabel("Second point");
		JLabel fillColorLabel = new JLabel();
		JColorArea color = new JColorArea(foregroundColor);
		JColorArea filledCircleColor = new JColorArea(backgroundColor);

		/**
		 * 
		 */
		public ChangeProperties() {
			panel.setLayout(new GridLayout(4, 2));
			panel.add(firstLabel);
			panel.add(firstText);
			panel.add(secondLabel);
			panel.add(secondText);
			panel.add(new JLabel("Color"));
			panel.add(color);
			fillColorLabel.setText("Fill color: ");
			panel.add(fillColorLabel);
			panel.add(filledCircleColor);
			firstLabel.setText("First point");
			secondLabel.setText("Second point:");
			fillColorLabel.setVisible(true);
			filledCircleColor.setVisible(false);
		}

		protected final synchronized JLabel getSecondLabel() {
			return secondLabel;
		}

		protected final synchronized JLabel getFirstLabel() {
			return firstLabel;
		}

		protected final synchronized JColorArea getColor() {
			return color;
		}

		protected final synchronized JTextField getFirstText() {
			return firstText;
		}

		protected final synchronized JTextField getSecondText() {
			return secondText;
		}

		protected final synchronized JLabel getFillColorLabel() {
			return fillColorLabel;
		}

		protected final synchronized JColorArea getFilledCircleColor() {
			return filledCircleColor;
		}

		protected final synchronized JPanel getPanel() {
			return panel;
		}
	}
}
