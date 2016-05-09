/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * Razred u kojem se crta krug. Razred krug nasljeđuje razred ElipsastiLikovi
 * 
 */
public class Circle extends ElipsastiLikovi {

	/**
	 * Kreira novi krug
	 * 
	 * @param x
	 *            Pozicija središta kruga na x osi
	 * @param y
	 *            Pozicija središta kruga na y osi
	 * @param radijus
	 *            Duljina radijusa kruga
	 */
	public Circle(int x, int y, int radijus) {
		if (radijus < 1) {
			throw new IllegalArgumentException(
					"Radijus ne može biti manji od 1");
		}
		this.x = x;
		this.y = y;
		this.verRadijus = this.horRadijus = radijus;
	}

	/**
	 * 
	 * @return Radijus kruga
	 */
	public int getRadijus() {
		return horRadijus;
	}

	/**
	 * Postavlja radijus kruga
	 * 
	 * @param radijus
	 *            Duljina željenog radijusa
	 */
	public void setRadijus(int radijus) {
		if (radijus < 1) {
			throw new IllegalArgumentException(
					"Radijus ne smije biti manji od 1");
		}
		this.horRadijus = this.verRadijus = radijus;
	}
}
