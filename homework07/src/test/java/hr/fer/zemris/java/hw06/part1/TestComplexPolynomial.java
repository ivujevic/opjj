package hr.fer.zemris.java.hw06.part1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestComplexPolynomial {

	@Test
	public void testOrder() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2), new Complex(3, 3));
		assertTrue("Greška u metodi order", 3 == pol.order());
	}

	@Test
	public void testMultiply() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex(1, 0), new Complex(2, 0));
		ComplexPolynomial rez = pol.multiply(pol);
		assertTrue("Greška u metodi multiply", rez.factors[0].re == 1
				&& rez.factors[1].re == 4 && rez.factors[2].re == 4);
	}

	@Test
	public void testDerive() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex(1, 0), new Complex(2, 0),new Complex(3, 0));
		ComplexPolynomial rez = pol.derive();
		assertTrue("Greška u metodi derive", rez.factors[0].re == 2
				&& rez.factors[1].re == 6);
	}

	@Test
	public void testApply() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex(1, 0), new Complex(2, 0),new Complex(3, 0));
		Complex rez = pol.apply(new Complex(1, 0));
		assertTrue("Greška u metodi apply", rez.re==6 && rez.im == 0);
	}

	@Test
	public void testToString() {
		ComplexPolynomial pol = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 0));
		assertTrue("Greška u metodi toString", pol.toString().equals("2.0z^1 + (1.0 + i1.0)"));
	}

}
