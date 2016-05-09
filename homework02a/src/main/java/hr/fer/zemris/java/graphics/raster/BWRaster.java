/**
 * 
 */
package hr.fer.zemris.java.graphics.raster;

/**
 * Raster interface
 * 
 */
public interface BWRaster {

	/**
	 * Određivanje dimenzija rastera
	 * 
	 * @return Širinu rastera
	 */

	public int getWidth();

	/**
	 * Određivanje dimanzija rastera
	 * 
	 * @return Visinu rastera
	 */
	public int getHeight();

	/**
	 * Gasi sve piksele u rasteru
	 */
	public void clear();

	/**
	 * Ako je <code>flipping mode</code> isključen tada se pozivom pale pikseli
	 * na određenoj lokaciji Ako je <code>flipping mode</code> uključen tada se
	 * pozivom mijenja stanje trenutnog piksela
	 * 
	 * @param x
	 *            Pozicija x osi
	 * @param y
	 *            Pozicija y osi
	 */
	public void turnOn(int x, int y);

	/**
	 * Ako je lokacija valjana tada gasi piksele na toj lokaciji
	 * 
	 * @param x
	 *            Pozicija na x osi
	 * @param y
	 *            Pozicija na y osi
	 */
	public void turnOff(int x, int y);

	/**
	 * Pali <code>enable mode</code>
	 */
	public void enableFlipMode();

	/**
	 * Gasi <code>enable mode</code>
	 */
	public void disableFlipMode();

	/**
	 * Provjerava piksel na danoj lokaciji
	 * 
	 * @param x
	 *            Pozicija na x osi
	 * @param y
	 *            Pozicija na y osi
	 * @return <code>true</code> ako je upaljen ako je izgašen vraća
	 *         <code>False</code>
	 */
	public boolean isTurnedOn(int x, int y);
}
