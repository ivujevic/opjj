/**
 * 
 */
package hr.fer.zemris.java.hw06.part1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Ivan
 *
 */
public class TestComplex {

	@Test
	public void testModule() {
		Complex number = new Complex(3, 4);
		assertTrue("Greška u modulu broja", 5== number.module());
	}
	
	@Test
	public void testMultiply() {
		Complex number = new Complex(3, 4);
		number= number.multiply(number);
		assertTrue("Greška u množenju", number.re == -7 && number.im==24);
	}
	
	@Test
	public void testDivide() {
		Complex number = new Complex(1,2);
		Complex number1 = new Complex(1,-2);
		Complex rez = number.divide(number1);
		assertTrue("Greška u dijeljenju", rez.re==-0.6 && rez.im==0.8);
	}
	
	@Test
	public void testAdd() {
		Complex number = new Complex(1,2);
		Complex rez = number.add(number);
		assertTrue("Greška u zbrajanju", rez.re==2 && rez.im==4);
	}
	
	@Test
	public void testSub() {
		Complex number = new Complex(1,2);
		Complex rez = number.sub(number);
		assertTrue("Greška u oduzimanju", rez.re==0 && rez.im==0);
	}
	
	@Test
	public void testNegate(){
		Complex number = new Complex(1,1);
		number = number.negate();
		assertTrue("Greška u negiranju", number.re==-1 && number.im==-1);
	}
	@Test
	public void testExponent() {
		Complex number = new Complex(2,2);
		Complex rez = number.exponent(0);
		assertTrue("Greška u potenciranju s 0", rez.re==1.0 && rez.im==0);
		rez = number.exponent(1);
		assertTrue("Greška u potenciranju s 1", rez.re==2 && rez.im==2);
	}
	
	@Test
	public void testToString() {
		Complex number = new Complex(1,1);
		String s = number.toString();
		assertTrue("Pogreška u metodu toString", s.equals("1.0 + i1.0"));
		
		Complex number1 = new Complex(1,-1);
		s = number1.toString();
		assertTrue("Pogreška u metodi toString", s.equals("1.0 - i1.0"));
	}
}
