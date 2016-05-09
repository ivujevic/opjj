/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JOptionPane;

/**
 * Razred u kojem je implementirano crtanje ispunjenog kruga
 * 
 * @author Ivan
 * 
 */
public class FilledCircle extends Circle {

	/**
	 * Brojač stvorenih krugova
	 */
	static int count = 0;

	/**
	 * Ime kruga
	 */
	String name;

	/**
	 * Konstruktor za krug
	 */
	public FilledCircle() {
		this.setFirstPoint(null);
		this.setSecondPoint(null);
	}

	/**
	 * Konstruktor
	 * 
	 * @param center
	 *            Centar kruga
	 * @param secondPoint
	 *            Točka na kružnici
	 */
	public FilledCircle(final Point center, final Point secondPoint) {
		this.firstPoint = center;
		this.secondPoint = secondPoint;
		radius = firstPoint.distance(secondPoint);
		count++;
		name = "FilledCircle" + String.valueOf(count);
	}

	/**
	 * Konstruktor
	 * 
	 * @param center
	 *            centar kruga
	 * @param radius
	 *            Radijus kruga
	 * @param foregroundColor
	 *            boja kojom je kružnica nacrtana
	 * @param backgroundColor
	 *            boja kojom je krug ispunjen
	 */
	public FilledCircle(final Point center, final double radius, final Color foregroundColor, final Color backgroundColor) {
		this.firstPoint = center;
		this.radius = radius;
		this.secondPoint = new Point(this.firstPoint.x, this.firstPoint.y + (int) radius);
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		count++;
		name = "FilledCircle" + String.valueOf(count);
	}

	@Override
	public final void draw(final Graphics g, final Point firstPoint, final Point secondPoint) {
		g.setColor(this.foregroundColor);
		int radius = (int) Math.abs(firstPoint.distance(secondPoint));
		g.setColor(this.backgroundColor);
		g.fillOval(firstPoint.x - radius, firstPoint.y - radius, 2 * radius, 2 * radius);

	}

	@Override
	public final void draw(final Graphics g) {
		g.setColor(this.foregroundColor);
		int radius = (int) Math.abs(firstPoint.distance(secondPoint));
		g.setColor(this.backgroundColor);
		g.fillOval(firstPoint.x - radius, firstPoint.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public final String toString() {
		return name;
	}

	@Override
	public final void showDialog() {
		while (true) {

			ChangeProperties properties = new ChangeProperties();
			properties.getFirstLabel().setText("Center");
			properties.getSecondLabel().setText("Radius");
			properties.getSecondText().setText(String.valueOf((int) radius));
			properties.getFillColorLabel().setVisible(true);
			properties.getFilledCircleColor().setVisible(true);
			int n = JOptionPane.showConfirmDialog(null, properties.getPanel(), "Change properties",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (n == JOptionPane.OK_OPTION) {

				try {
					change(properties);
					String secondPoint = properties.getSecondText().getText();
					this.radius = Integer.valueOf(secondPoint);
					this.secondPoint = new Point(this.firstPoint.x, this.firstPoint.y + (int) this.radius);
					break;
				} catch (NumberFormatException e) {
					JOptionPane.showConfirmDialog(null, "Unijeli ste krive podatke", "Pogreška",
							JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				break;
			}
		}

	}
}
