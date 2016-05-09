/**
 * Paket sadrzi sve razrede potrebne za rad s rasterom
 */
package hr.fer.zemris.java.graphics.raster;


public class BWRasterMem implements BWRaster {

	/**
	 * Varijabla koja prdstavlja stanje u kojem se <code>Flip mode</code> nalizi
	 */
	public boolean stanjeFlipMode = false;

	/**
	 * Varijabla koja prestavlja širinu rastera
	 */
	private int sirina;

	/**
	 * Varijabla koja prestavlja visinu rastera
	 */
	private int visina;

	/**
	 * Varijabla koja prestavlja stanje u kojem se pojedini piksel nalazi
	 */
	private boolean[][] stanje;

	/**
	 * Memorija raster
	 * 
	 * @param sirina
	 *            Širina rastera u kojem se crta
	 * @param visina
	 *            Visina rastera u kojem se crta
	 */
	public BWRasterMem(int sirina, int visina) {
		this.sirina = sirina;
		this.visina = visina;
		this.stanje = new boolean[this.visina + 1][this.sirina + 1];
	}

	@Override
	public int getWidth() {
		return this.sirina;
	}

	@Override
	public int getHeight() {
		return this.visina;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.visina; i++) {
			for (int j = 0; j < this.sirina; j++) {
				stanje[i][j] = false;
			}
		}

	}

	@Override
	public void turnOn(int x, int y) {
		if (stanjeFlipMode == true) {
			stanje[y][x] = !stanje[y][x];
		} else {
			stanje[y][x] = true;
		}

	}

	@Override
	public void turnOff(int x, int y) {
		stanje[y][x] = false;

	}

	@Override
	public void enableFlipMode() {
		stanjeFlipMode = true;

	}

	@Override
	public void disableFlipMode() {
		stanjeFlipMode = false;

	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		return stanje[y][x];
	}

}
