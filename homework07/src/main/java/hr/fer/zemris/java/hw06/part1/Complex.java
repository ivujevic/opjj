/**
 * 
 */
package hr.fer.zemris.java.hw06.part1;

/**
 * Razred u kojem su implementirane metode za rad s kompleksnim brojevima.
 * 
 * @author Ivan
 * 
 */
public class Complex {

	/**
	 * Realni dio kompleksnog broja.
	 */
	double re;

	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	double im;

	/**
	 * Stvara kompleksni broj 0.
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Stvara kompleksni broj 1 + 0i.
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Stvara kompleksni broj -1 + 0i.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Stvara kompleksni broj 0 + 1i.
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Stvara kompleksni broj 0 - 1i.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Konstruktor za stvaranje kompleksnog broja 0 + 0i.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Konstruktor za stvaranje kompleksnog broja.
	 * 
	 * @param re
	 *            Realni dio kompleksnog broja
	 * @param im
	 *            Imaginarni dio kompleksnog broja
	 */
	public Complex(final double re, final double im) {

		this.re = re;
		this.im = im;
	}

	/**
	 * Metoda za računanje modula kompleksnog broja.
	 * 
	 * @return modul kompleksnog broja
	 */
	public final double module() {
		return Math.sqrt(this.re * this.re + this.im * this.im);
	}

	/**
	 * Metoda koja množi dva kompleksna broja.
	 * 
	 * @param c
	 *            Kompleksni broj s kojim se množi
	 * @return Umnožak dva kompleksna broja
	 */
	public final Complex multiply(final Complex c) {
		Complex result = new Complex();
		result.re = this.re * c.re - this.im * c.im;
		result.im = this.re * c.im + this.im * c.re;
		return result;
	}

	/**
	 * Metoda koja dijeli dva kompleksna broja.
	 * 
	 * @param c
	 *            Kompleksni broj s kojim se dijeli
	 * @return Rezultat dijeljenja dva kompleksna broja
	 */
	public final Complex divide(final Complex c) {
		Complex numerator = this.multiply(new Complex(c.re, -c.im));
		Complex denominator = c.multiply(new Complex(c.re, -c.im));
		return new Complex(numerator.re / denominator.re, numerator.im / denominator.re);
	}

	/**
	 * Metoda koja zbraja dva kompleksna broja.
	 * 
	 * @param c
	 *            Kompleksni broj s kojim se zbraja
	 * @return Zbroj dva kompleksna broja
	 */
	public final Complex add(final Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Metoda koja oduzima dva kompleksna broja.
	 * 
	 * @param c
	 *            Kompleksni broj koji se oduzima.
	 * @return Razlika dva kompleksna broja
	 */
	public final Complex sub(final Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Metoda koja negira dani kompleksni broj.
	 * 
	 * @return Negiran kompleksni broj
	 */
	public final Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		if (this.re != 0) {
			builder.append(Double.toString(this.re) + "");
		}

		if (this.im < 0) {
			builder.append(" - i");
		}
		else if (this.im > 0) {
			builder.append(" + i");
		}
		if (this.im != 0) {
			builder.append(Double.toString(Math.abs(this.im)));
		}
		return builder.toString();
	}

	/**
	 * Metoda koja potencira kompleksni broj¸.
	 * 
	 * @param exp
	 *            Potencija kompleksnog broja
	 * @return Rezultat potenciranja
	 */
	public final Complex exponent(final int exp) {
		if (exp == 0) {
			return Complex.ONE;
		}
		Complex res = new Complex(1, 0);
		for (int i = 0; i < exp; i++) {
			res = res.multiply(this);
		}
		return res;
	}
}
