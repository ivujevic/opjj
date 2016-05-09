/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Razred pravokutnih likova. Kvadrat i pravokutnik
 * 
 */
public class PravokutniLikovi extends GeometricShape {

	/**
	 * Varijabla u koju se sprema x koordinate gornjeg lijevog kuta pravokutnog
	 * lika
	 */
	protected int x;

	/**
	 * Varijabla u koju se sprema y koordinata gornjeg lijevog kuta pravokutnog
	 * lika
	 */
	protected int y;

	/**
	 * Varijabla u koju se sprema širina pravokutnog lika
	 */
	protected int sirina;

	/**
	 * Varijabla u koju se sprema visina pravokutnog lika
	 */
	protected int visina;

	/**
	 * 
	 * @return x koordinata gornje lijeve točke
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return y koordinata gornje lijeve točke
	 */
	public int getY() {
		return y;
	}

	/**
	 * Postavlja x koordinata gornje lijeve točke
	 * 
	 * @param x
	 *            Vrijednost x koordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Postavlja y koordinatu gornje lijeve točke
	 * 
	 * @param y
	 *            Vrijednost y koordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (x < 0 || y < 0) {
			return false;
		}
		if (x >= this.x && x < this.x + sirina && y >= this.y
				&& y < this.y + visina) {
			return true;
		}

		return false;
	}

	@Override
	public void draw(BWRaster r) {
		for (int y = this.y; y <= this.y + this.visina; y++) {
			for (int x = this.x; x <= this.x + this.sirina; x++) {
				if (x >= 0 && y >= 0) {
					r.turnOn(x, y);
				}
			}
		}
	}
}
