/**
 * 
 */
package hr.fer.zemris.java.hw06.part1;

/**
 * Razred u kojem se implementirane metode za rad s polinomima.
 * 
 * @author Ivan
 * 
 */
public class ComplexPolynomial {

	/**
	 * Koeficijenti polinoma.
	 */
	Complex[] factors;

	/**
	 * Konstruktor koje stvara novi polinom s predanim koeficijentima.
	 * 
	 * @param factors
	 *            Koeficijenti polinoma
	 */
	public ComplexPolynomial(final Complex... factors) {
		super();
		this.factors = factors;
	}

	/**
	 * Metoda koja računa red polinoma.
	 * 
	 * @return Red polinoma
	 */
	public final short order() {
		return (short) this.factors.length;
	}

	/**
	 * Metoda koja množi dva polinoma.
	 * 
	 * @param p
	 *            Polinom s kojim se množi
	 * @return Umnožak dva polinoma
	 */
	public final ComplexPolynomial multiply(final ComplexPolynomial p) {

		Complex[] resultFactor = new Complex[this.factors.length + p.factors.length - 1];

		for (int i = resultFactor.length - 1; i >= 0; i--) {
			resultFactor[i] = new Complex(0, 0);
		}

		for (int i = this.factors.length - 1; i >= 0; i--) {
			for (int j = p.factors.length - 1; j >= 0; j--) {
				resultFactor[i + j] = resultFactor[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(resultFactor);
	}

	/**
	 * Metoda koja derivira polinom.
	 * 
	 * @return Derivacija polinoma.
	 */
	public final ComplexPolynomial derive() {

		Complex[] resultFactor = new Complex[this.factors.length - 1];

		for (int i = resultFactor.length - 1; i >= 0; i--) {
			resultFactor[i] = new Complex(0, 0);
		}

		for (int i = this.factors.length - 1; i > 0; i--) {
			resultFactor[i - 1] = resultFactor[i - 1].add(this.factors[i].multiply(new Complex(i, 0)));
		}
		return new ComplexPolynomial(resultFactor);
	}

	/**
	 * Metoda koja računa vrijednost polinoma danoj točki.
	 * 
	 * @param z
	 *            Točka u kojoj se računa vrijednost polinoma
	 * @return Vrijednost polinoma
	 */
	public final Complex apply(final Complex z) {
		Complex result = new Complex();
		for (int i = this.factors.length - 1; i >= 0; i--) {
			result = result.add(this.factors[i].multiply(z.exponent(i)));
		}
		return result;
	}

	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = this.factors.length - 1; i >= 0; i--) {

			if (this.factors[i].toString().length() != 0) {

				if (this.factors[i].re != 0 && this.factors[i].im != 0) {
					builder.append("(" + this.factors[i].toString() + ")");
				}
				else {
					builder.append(this.factors[i].toString());
				}
				if (i != 0) {
					builder.append("z^" + i + " + ");
				}

			}
		}
		return builder.toString();
	}
}
