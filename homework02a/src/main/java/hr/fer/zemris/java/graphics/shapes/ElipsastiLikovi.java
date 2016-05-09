/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * 
 * Razred koji predstavlja likove oblika elipse. To su elipsa i krug
 */
public class ElipsastiLikovi extends GeometricShape {

	/**
	 * Varijabla u koju se sprema x koordinata središta elipsastog lika
	 */
	protected int x;

	/**
	 * Varijabla u koju se sprema y koordinata središta elipsastog lika
	 */
	protected int y;

	/**
	 * Varijabla u koju se sprema horizontalni radijus elipsastog lika
	 */
	protected int horRadijus;

	/**
	 * Varijabla u koju se sprema vertikalni radijus elipsastog lika
	 */
	protected int verRadijus;

	/**
	 * 
	 * @return x koordinata središta
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return y koordinata središta
	 */
	public int getY() {
		return y;
	}

	/**
	 * Postavlja x koordinatu središta
	 * 
	 * @param x
	 *            Vrijednost x koordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Postavlja y koordinatu središta
	 * 
	 * @param y
	 *            Vrijednost y koordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean containsPoint(int x, int y) {

		int prvi = (x - this.x) * (x - this.x) * verRadijus * verRadijus;
		int drugi = (y - this.y) * (y - this.y) * horRadijus * horRadijus;
		if (x < 0 || y < 0) {
			return false;
		}
		if (prvi + drugi <= verRadijus * verRadijus * horRadijus * horRadijus) {
			return true;
		}

		return false;
	}

	@Override
	public void draw(BWRaster r) {
		for (int y = this.y - this.verRadijus; y <= this.y + this.verRadijus; y++) {
			for (int x = this.x - this.horRadijus; x <= this.x
					+ this.horRadijus; x++) {
				if (this.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}
}
