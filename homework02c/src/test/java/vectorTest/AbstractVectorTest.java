/**
 * 
 */
package vectorTest;

import static org.junit.Assert.*;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Test;

public class AbstractVectorTest {

	@Test
	public void testAdd() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = new hr.fer.zemris.linearna.Vector(polje);
		vektor.add(vektor1);
		for (int i = 0; i < 10000; i++) {
			assertEquals(2 * i, vektor.get(i), 0.0000000000001);
		}
		try {
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.add(vektor3);
		} catch (IncompatibleOperandException e) {

		}
	}
	@Test
	public void testNAdd() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = new hr.fer.zemris.linearna.Vector(polje);
		vektor.nAdd(vektor1);

		for (int i = 0; i < 10000; i++) {
			assertEquals(i, vektor.get(i), 0.0000000000001);
		}

		try {
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.nAdd(vektor3);
		} catch (IncompatibleOperandException e) {

		}
	}

	@Test
	public void testSub() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = new hr.fer.zemris.linearna.Vector(polje);
		vektor.sub(vektor1);
		for (int i = 0; i < 10000; i++) {
			assertEquals(0, vektor.get(i), 0.0000000000001);
		}
		try {
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.sub(vektor3);
		} catch (IncompatibleOperandException e) {
			
		}
		
	}
	@Test
	public void testNSubb() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = new hr.fer.zemris.linearna.Vector(polje);
		vektor.nSub(vektor1);

		for (int i = 0; i < 10000; i++) {
			assertEquals(i, vektor.get(i), 0.0000000000001);
		}
		Throwable c = null;

		try {
			IVector vektor3 = new Vector(1, 2, 3, 4);
			vektor1.nSub(vektor3);
		} catch (IncompatibleOperandException e) {
			c = e;
		}
		assertTrue(c != null);
	}

	@Test
	public void testScalarMultiply() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);

		vektor.scalarMultiply(5);
		for (int i = 0; i < 10000; i++) {
			assertEquals(5 * i, vektor.get(i), 0.0000000000001);
		}
	}

	@Test
	public void testNScalarMultiply() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		vektor.nScalarMultiply(5);
		for (int i = 0; i < 10000; i++) {
			assertEquals(i, vektor.get(i), 0.0000000000001);
		}
	}
	
	@Test
	public void testNorm() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		assertEquals(573.018, vektor.norm(), 0.01);
	}

	@Test
	public void testNormalize() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		vektor.normalize();
		assertEquals(1, vektor.norm(),0.00000001);
	}
	
	@Test
	public void testNNormalize() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = vektor.nNormalize();
		
		for (int i = 0; i < 100; i++) {
			assertEquals(i/vektor.norm(), vektor1.get(i),0.000);
		}
	}
	
	@Test
	public void testCosine() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		assertEquals(1, vektor.cosine(vektor),0.0);
	}
	
	@Test
	public void testScalarProduct() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		assertEquals(328350, vektor.scalarProduct(vektor),0.0);
		IVector vektor1 = new Vector(1,2,3);
		Throwable c = null;
		try {
			vektor.scalarProduct(vektor1);
		} catch(IncompatibleOperandException e) {
			c=e;
		}
		assertTrue(c!=null);
	}
	
	@Test
	public void testnVectorProduct() {
		IVector vektor = new Vector(1,2,3);
		IVector vektorRez = new Vector(0,0,0);
		assertArrayEquals(vektorRez.toArray(), vektor.nVectorProduct(vektor).toArray(), 0.0001);
		
		IVector vektor1 = new Vector(1,2,3,4);
		Throwable c=null;
		try {
			vektorRez = vektor.nVectorProduct(vektor1);
		} catch(IncompatibleOperandException e) {
			c=e;
		}
		assertTrue(c!=null);
		c=null;
		try {
			vektorRez = vektor1.nVectorProduct(vektor);
		} catch(IncompatibleOperandException e) {
			c=e;
		}
		assertTrue(c!=null);
	}
	
	@Test
	public void nFromHomogeneus() {
		double[] polje = new double[100];
		for (int i = 0; i < 100; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = vektor.nFromHomogeneus();
		for (int i = 0; i < 99; i++) {
			assertEquals(vektor.get(i)/99, vektor1.get(i), 0.00001);
		}
	}
	
	@Test
	public void testToArray() {
		double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		assertArrayEquals(polje, vektor.toArray(), 0.0000);
	}
	
	@Test
	public void testCopyPart() {
	    double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		IVector vektor1 = vektor.copyPart(20000);
		for (int i = 0; i < 20000; i++) {
			if(i<10000) {
			    assertEquals(i, vektor1.get(i), 0.0);
			}
			else {
			    assertEquals(0, vektor1.get(i),0.0);
			}
		}
		Throwable c=null;
		try {
		    vektor.copyPart(-5);
		} catch (IllegalArgumentException e) {
		    c=e;
		}
		assertTrue(c!=null);
	}
	
	@Test
	public void testToRowMatrix() {
	    double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		
		IMatrix matrica = vektor.toRowMatrix(false);
		for (int i = 0; i < 10000; i++) {
			assertEquals(i, matrica.get(0,i),0.0);
		}
		
		IMatrix matrica1 = vektor.toRowMatrix(true);
		for (int i = 0; i < 10000; i++) {
			matrica1.set(0, i, 2*i);
		}
		for(int i=0;i<10000;i++) {
		    assertEquals(2*i, vektor.get(i),0.0);
		}
	}
	
	@Test
	public void testToColumnMatrix() {
	    double[] polje = new double[10000];
		for (int i = 0; i < 10000; i++) {
			polje[i] = i;
		}
		IVector vektor = new hr.fer.zemris.linearna.Vector(polje);
		
		IMatrix matrica = vektor.toColumnMatrix(false);
		for (int i = 0; i < 10000; i++) {
			assertEquals(i, matrica.get(i,0),0.0);
		}
		
		IMatrix matrica1 = vektor.toColumnMatrix(true);
		for (int i = 0; i < 10000; i++) {
			matrica1.set(i, 0, 2*i);
		}
		for(int i=0;i<10000;i++) {
		    assertEquals(2*i, vektor.get(i),0.0);
		}
	}
	
	@Test
	public void testToString() {
	    IVector vektor = new Vector(1.5245,4.55645,7.12356,14.5651);
	    String ispis = vektor.toString();
	    String usporedi ="( 1.524, 4.556, 7.124, 14.565)";
	    assertEquals(ispis, usporedi);
	}
}
