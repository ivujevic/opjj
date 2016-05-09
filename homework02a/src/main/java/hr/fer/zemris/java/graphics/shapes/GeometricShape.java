/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Razred geometrijskih likova
 * 
 */
public abstract class GeometricShape {

	/**
	 * 
	 * @param x
	 *            Pozicija na x osi
	 * @param y
	 *            Pozivija na y osi
	 * @return <code>True</code> ako je točka unutar lika, <code>False</code>
	 *         ako je točka izvan lika
	 */
	public abstract boolean containsPoint(int x, int y);

	/**
	 * Crta Geometrijski lik na raster
	 * 
	 * @param r
	 *            Raster
	 */
	public void draw(BWRaster r) {

		int sirina = r.getWidth();
		int visina = r.getHeight();
		for (int y = 0; y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (this.containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}

	}
}
