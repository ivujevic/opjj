/**
 * 
 */
package hr.fer.zemris.java.hw06.part1.fraktali;

import hr.fer.zemris.java.hw06.part1.Complex;
import hr.fer.zemris.java.hw06.part1.ComplexPolynomial;
import hr.fer.zemris.java.hw06.part1.ComplexRootedPolynomial;

/**
 * Razred u kojem se nalazi metoda koja računa fraktale.
 * 
 * @author Ivan
 * 
 */
public class NewtonRaphson {

	/**
	 * Najveći broj iteracija.
	 */
	private static final int MAXITER = 16 * 16 * 16;

	/**
	 * Prag za koji se gleda kongruencija.
	 */
	private static final double CONVERGECNE_TRESHOLD = 0.002;

	/**
	 * Metoda koja računa fraktale
	 * 
	 * @param remin
	 * @param remax
	 * @param immin
	 * @param immax
	 * @param width
	 * @param height
	 * @param ymin
	 * @param ymax
	 * @param data
	 * @param rootedPolynom
	 */
	public static void racunaj(final double remin, final double remax, final double immin, final double immax, final int width, final int height,
			final int ymin, final int ymax, final short[] data, final ComplexRootedPolynomial rootedPolynom) {

		int offset = ymin * width;
		ComplexPolynomial pol = rootedPolynom.toComplexPolynom();
		ComplexPolynomial derive = pol.derive();
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {

				double cre = x * (remax - remin) / (width - 1) + remin;
				double cim = (height - 1 - y) * (immax - immin) / (height - 1) + immin;
				Complex zn = new Complex(cre, cim);
				Complex zn1 = null;
				int iter = 0;
				double module = 0;
				do {
					zn1 = zn.sub(pol.apply(zn)
							.divide(derive.apply(zn)));
					iter++;
					module = zn1.sub(zn).module();
					zn = zn1;
				} while (module > CONVERGECNE_TRESHOLD && iter < MAXITER);
				short index = (short) rootedPolynom.indexOfClosestRootFor(zn1, CONVERGECNE_TRESHOLD);
				if (index == -1) {
					data[offset++] = 0;
				}
				else {
					data[offset++] = (short) (index);
				}
			}
		}
	}
}
