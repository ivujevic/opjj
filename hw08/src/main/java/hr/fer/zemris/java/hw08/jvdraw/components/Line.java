/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JOptionPane;

/**
 * Razred koji služi za crtanje linije
 * 
 * @author Ivan
 * 
 */
public class Line extends GeometricalObject {

	/**
	 * Broj stvorenih linija.
	 */
	static int count = 0;

	protected static synchronized int getCount() {
		return count;
	}

	/**
	 * Ime linije.
	 */
	String name;

	/**
	 * Konstruktor
	 */
	public Line() {
		firstPoint = secondPoint = null;
	}

	/**
	 * Konstruktor
	 * 
	 * @param firstPoint
	 *            Prva točka.
	 * @param secondPoint
	 *            Druga točka.
	 */
	public Line(Point firstPoint, Point secondPoint) {
		this.setFirstPoint(firstPoint);
		this.setSecondPoint(secondPoint);
		count++;
		name = "Line" + String.valueOf(count);
	}

	/**
	 * Konstruktor
	 * 
	 * @param firstPoint
	 *            Prva točka
	 * @param secondPoint
	 *            Druga točka
	 * @param foregroundColor
	 *            Boja
	 */
	public Line(Point firstPoint, Point secondPoint, Color foregroundColor) {
		this.setFirstPoint(firstPoint);
		this.setSecondPoint(secondPoint);
		this.foregroundColor = foregroundColor;
		count++;
		name = "Line" + String.valueOf(count);
	}

	/**
	 * Metoda koja crta liniju
	 */
	public void draw(Graphics g) {
		g.setColor(this.foregroundColor);
		Point firstPoint = this.getFirstPoint();
		Point secondPoint = this.getSecondPoint();
		if (firstPoint == null || secondPoint == null) {
			return;
		}
		g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
	}

	/**
	 * Metoda koja crta liniju
	 * 
	 * @param g
	 *            gdje se crta
	 * @param firstPoint
	 *            prva točka
	 * @param secondPoint
	 *            druga točka
	 */
	public void draw(Graphics g, Point firstPoint, Point secondPoint) {
		g.setColor(this.foregroundColor);
		if (firstPoint == null || secondPoint == null) {
			return;
		}
		g.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void showDialog() {
		while (true) {

			ChangeProperties properties = new ChangeProperties();

			int n = JOptionPane.showConfirmDialog(null, properties.getPanel(), "Change properties",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (n == JOptionPane.OK_OPTION) {

				try {
					change(properties);
					String secondPoint = properties.getSecondText().getText();
					String[] point = secondPoint.split(",");
					this.setSecondPoint(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
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
