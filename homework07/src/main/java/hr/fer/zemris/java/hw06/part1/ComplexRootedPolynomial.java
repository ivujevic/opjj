/**
 * 
 */
package hr.fer.zemris.java.hw06.part1;

/**
 * Metoda koja implementira metoda za rad s polinomom prikazanim preko korijena.
 * 
 * @author Ivan
 * 
 */
public class ComplexRootedPolynomial {

	/**
	 * Korijeni polinoma.
	 */
	Complex[] roots;

	/**
	 * Konstruktor koji stvara novi polinom iz danih korijena.
	 * 
	 * @param roots
	 *            Korijeni polinoma
	 */
	public ComplexRootedPolynomial(final Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Metoda koja računa vrijednost polinoma u danoj točki.
	 * 
	 * @param z
	 *            Točka u kojoj se računa vrijednost polinoma.
	 * @return Vrijednost polinoma u točki.
	 */
	public final Complex apply(final Complex z) {
		Complex res = new Complex();
		res.re = 1;
		res.im = 1;
		for (Complex p : roots) {
			res = res.multiply(z.sub(p));
		}
		return res;
	}

	/**
	 * Metoda koja polinom zapisan preko korijena pretvara u "standardan" oblik.
	 * 
	 * @return Polinom zapisan preko potencija
	 */
	public final ComplexPolynomial toComplexPolynom() {

		// Stvori polinom reda 0 te ga množi s faktorima polinoma zapisanog preko korijena
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		for (Complex r : roots) {
			Complex[] factors = new Complex[2];
			factors[1] = new Complex(1, 0);
			factors[0] = new Complex(r.negate().re, r.negate().im);
			result = result.multiply(new ComplexPolynomial(factors));
		}
		return result;
	}

	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		for (Complex r : roots) {
			builder.append("[" + "z - (" + r.toString() + ")]");
		}
		return builder.toString();
	}

	/**
	 * Metoda koja vraća index najbližeg korijena za dani kompleksni broj koji je unutar zadanog razmaka.
	 * 
	 * @param z
	 *            Kompleksni broj kojem se traži najbliži korijen.
	 * @param treshold
	 *            Razmak unutar kojeg se mora nalaziti korijen.
	 * @return Index najbližeg korijena.
	 */
	public final int indexOfClosestRootFor(final Complex z, final double treshold) {

		int index = -1;
		double[] distance = new double[roots.length];

		for (int i = 0; i < roots.length; i++) {
			distance[i] = z.sub(roots[i]).module();
		}

		double min = distance[0];
		for (int i = 0; i < roots.length; i++) {
			if (distance[i] < min) {
				min = distance[i];
				index = i + 1;
			}
		}
		if (min < treshold) {
			return index;
		}
		else {
			return -1;
		}
	}
}
