package hr.fer.zemris.java.hw06.part1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestComplexRootedPolynomial {

	
	@Test
	public void testApply() {
		 ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(1, 0), new Complex(-1, 0));
		 assertTrue("Pogreška u naredbi Apply",-9== p.apply(new Complex(2, 2)).re && 7 ==p.apply(new Complex(2, 2)).im );
	}

	@Test
	public void testToComplexPolynom() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(1, 4), new Complex(2, 0));
		
        ComplexPolynomial cp = p.toComplexPolynom();
        assertTrue("Pogreška u naredbi toComplexPolynom", cp.toString().equals("1.0z^2 + (-3.0 - i4.0)z^1 + (2.0 + i8.0)"));
	}

	@Test
	public void testToString() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(5, -1), new Complex(3, 2));
		assertTrue("Pogreška u naredbi toString", p.toString().equals("[z - (5.0 - i1.0)][z - (3.0 + i2.0)]"));
	}

	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(-2, 1),new Complex(4, -1));
        int index = p.indexOfClosestRootFor(new Complex(2, 0), 0.1);
        assertTrue("Pogreška u naredbi indexOfClosestRootFor", index ==-1);
	}

}
