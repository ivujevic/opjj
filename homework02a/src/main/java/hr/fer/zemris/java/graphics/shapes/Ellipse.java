/**
 * Paket sadrži razrede za likove koji se crtaju
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * Razred za elipsu
 * 
 */
public class Ellipse extends ElipsastiLikovi {

	/**
	 * Kreira novu elipsu
	 * 
	 * @param x
	 *            Pozicija središta na x osi
	 * @param y
	 *            Pozicija središta na y osi
	 * @param horRadijus
	 *            Duljina horizontalnog radijusa
	 * @param verRadijus
	 *            Duljina vertikalnog radijusa
	 */
	public Ellipse(int x, int y, int horRadijus, int verRadijus) {
		if (horRadijus <= 1 || verRadijus <= 1) {
			throw new IllegalArgumentException(
					"Radijusi ne smiju biti manji od 1");
		}
		this.x = x;
		this.y = y;
		this.horRadijus = horRadijus;
		this.verRadijus = verRadijus;
	}

	/**
	 * 
	 * @return Duljina horizontalnog radijusa
	 */
	public int getHorRadijus() {
		return horRadijus;
	}

	/**
	 * 
	 * @return Duljina vertikalnog radijusa
	 */
	public int getVerRadijus() {
		return verRadijus;
	}

	/**
	 * Postavlja duljinu horizontalnog radijussa
	 * 
	 * @param horRadijus
	 *            Duljina horizontalnog radijusa
	 */
	public void setHorRadijus(int horRadijus) {
		if (horRadijus < 1) {
			throw new IllegalArgumentException(
					"Horizontalni radijus ne smije biti manji od 1");
		}
		this.horRadijus = horRadijus;
	}

	/**
	 * Postavlja duljinu vertikalnog radijusa
	 * 
	 * @param verRadijus
	 *            Duljina vertikalnog radijusa
	 */
	public void setVerRadijus(int verRadijus) {
		if (verRadijus < 1) {
			throw new IllegalArgumentException(
					"Vertikalni radijus ne smije biti manji od 1");
		}
		this.verRadijus = verRadijus;
	}
}
