/**
 * 
 */
package hr.fer.zemris.java.hw08.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JOptionPane;

/**
 * Razred u kojem se implementira crtanje kruga
 * 
 * @author Ivan
 * 
 */
public class Circle extends GeometricalObject {

	/**
	 * Radijus kruga
	 */
	public double radius = 0;

	public double getRadius() {
		return radius;
	}

	/**
	 * Brojač koliko je krugova do sada nacrtano
	 */
	static int count = 0;

	/**
	 * Ime trenutno stvorenog kruga
	 */
	String name;

	/**
	 * Konstruktor za krug
	 */
	public Circle() {
		this.setFirstPoint(null);
		this.setSecondPoint(null);
	}

	/**
	 * Konstruktor za krug koji prima dva argumenta
	 * 
	 * @param center
	 *            Koordinata centra kruga
	 * @param secondPoint
	 *            Koordinata točke na kružnici
	 */
	public Circle(Point center, Point secondPoint) {
		this.firstPoint = center;
		this.secondPoint = secondPoint;
		radius = firstPoint.distance(secondPoint);
		count++;
		name = "Circle" + String.valueOf(count);
	}

	/**
	 * Metoda za krug koja prima tri argumenta
	 * 
	 * @param center
	 *            Koordinata centra kruga
	 * @param radius
	 *            Radijus kruga
	 * @param foregroundColor
	 *            Boja kojom se crta okvir kruga
	 */
	public Circle(Point center, double radius, Color foregroundColor) {
		this.firstPoint = center;
		this.radius = radius;
		this.secondPoint = new Point(this.firstPoint.x, this.firstPoint.y + (int) radius);
		this.foregroundColor = foregroundColor;
		count++;
		name = "Circle" + String.valueOf(count);
	}

	/**
	 * Metoda koja crta krug
	 */
	public void draw(Graphics g) {
		g.setColor(this.foregroundColor);
		Point firstPoint = this.getFirstPoint();
		Point secondPoint = this.getSecondPoint();
		if (firstPoint == null || secondPoint == null) {
			return;
		}
		g.drawOval(this.getFirstPoint().x - (int) radius, this.getFirstPoint().y - (int) radius,
				(int) radius * 2, (int) radius * 2);
	}

	/**
	 * Metoda koja crta krug sa zadanim parametrima
	 * 
	 * @param g
	 *            Gdje se crta
	 * @param firstPoint
	 *            točka središta kruga
	 * @param secondPoint
	 *            točka na kružnici
	 */
	public void draw(Graphics g, Point firstPoint, Point secondPoint) {
		g.setColor(this.foregroundColor);
		if (firstPoint == null || secondPoint == null) {
			return;
		}
		int radius = (int) Math.abs(firstPoint.distance(secondPoint));
		g.drawOval(firstPoint.x - radius, firstPoint.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void showDialog() {
		while (true) {

			ChangeProperties properties = new ChangeProperties();
			properties.getFirstLabel().setText("Center");
			properties.getSecondLabel().setText("Radius");
			properties.getSecondText().setText(String.valueOf((int) radius));

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
