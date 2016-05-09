/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * Klasa za pravokutnik
 * 
 */
public class Rectangle extends PravokutniLikovi {

	/**
	 * Kreira novi pravokutnik1
	 * 
	 * @param x
	 *            Pozicija gornjeg lijevog kuta na x osi
	 * @param y
	 *            Pozicija gornjeg lijevog kuta na y osi
	 * @param sirina
	 *            Širina pravokutnika
	 * @param visina
	 *            Visina pravokutnika
	 */
	public Rectangle(int x, int y, int sirina, int visina) {
		if (sirina <= 0 || visina <= 0) {
			throw new IllegalArgumentException(
					"Ni visina ni širina ne smiju biti manji od 0");
		}

		this.x = x;
		this.y = y;
		this.sirina = sirina;
		this.visina = visina;
	}

	/**
	 * 
	 * @return Širina pravokutnika
	 */
	public int getSirina() {
		return sirina;
	}

	/**
	 * Postavlja širinu pravokutnika
	 * 
	 * @param sirina
	 *            Željena širina
	 */
	public void setSirina(int sirina) {
		if (sirina < 1) {
			throw new IllegalArgumentException(
					"Širina ne smije biti manja od 1");
		}
		this.sirina = sirina;
	}

	/**
	 * 
	 * @return Visinu pravouktnika
	 */
	public int getVisina() {
		return visina;
	}

	/**
	 * Postavlja visinu pravouktnika
	 * 
	 * @param visina
	 *            Željena visina
	 */
	public void setVisina(int visina) {
		if (visina < 1) {
			throw new IllegalArgumentException(
					"Visina ne smije biti manja od 1");
		}
		this.visina = visina;
	}
}
