/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * 
 * Razred za kvadrat
 */
public class Square extends PravokutniLikovi {

	/**
	 * Kreira novi kvadrat
	 * 
	 * @param x
	 *            Pozicija gornjeg lijevog kuta na x osi
	 * @param y
	 *            Pozicija gornjeg lijevog kuta na y osi
	 * @param velicina
	 *            Veličina stranice kvadrata
	 */
	public Square(int x, int y, int velicina) {

		if (velicina <= 0) {
			throw new IllegalArgumentException(
					"Veličina kvadrata ne može biti manja od 0");
		}

		this.x = x;
		this.y = y;
		this.visina = this.sirina = velicina;
	}

	/**
	 * 
	 * @return Veličina pravokutnika
	 */
	public int getVelicina() {
		return this.sirina;
	}

	/**
	 * Postavlja veličinu pravokutnika
	 * 
	 * @param velicina
	 *            Željena veličina
	 */
	public void setVelicina(int velicina) {
		if (velicina < 1) {
			throw new IllegalArgumentException(
					"Velicina ne smije biti manja od 1");
		}
		this.sirina = this.visina = velicina;
	}
}
