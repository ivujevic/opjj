/**
 * 
 */
package vectorTest;

import static org.junit.Assert.*;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import org.junit.Test;

public class VectorTest {

    @Test
    public void testGetSet() {
	double[] polje = new double[10000];
	for (int i = 0; i < 10000; i++) {
	    polje[i] = i;
	}
	IVector vektor = new Vector(true, true, polje);

	Throwable c = null;
	try {
	    vektor.set(40, 1655);
	} catch (IllegalAccessError e) {
	    c = e;
	}
	assertTrue(c != null);

	c = null;
	try {
	    vektor.get(-40);
	} catch (IllegalArgumentException e) {
	    c = e;
	}
	assertTrue(c != null);

	c = null;
	try {
	    vektor.get(12455);
	} catch (IllegalArgumentException e) {
	    c = e;
	}
	assertTrue(c != null);
	IVector vektor1 = new Vector(false, false, polje);
	for (int i = 0; i < 10000; i++) {
	    vektor1.set(i, 2 * i);
	}

	for (int i = 0; i < 10000; i++) {
	    assertEquals(2 * i, vektor1.get(i), 0.0);
	}

	IVector vektor2 = new Vector(polje);
	for (int i = 0; i < 10000; i++) {
	    assertEquals(i, vektor2.get(i), 0.0);
	}
	c = null;
	try {
	    vektor2.set(-5, 100);
	} catch (IllegalArgumentException e) {
	    c = e;
	}
	assertTrue(c != null);
    }
    
    @Test
    public void testGetDimension() {
	double[] polje = new double[10000];
	for (int i = 0; i < 10000; i++) {
	    polje[i] = i;
	}
	IVector vektor = new Vector(true, true, polje);
	assertEquals(10000, vektor.getDimension());
    }
    
    @Test
    public void testCopy() {
	double[] polje = new double[10000];
	for (int i = 0; i < 10000; i++) {
	    polje[i] = i;
	}
	IVector vektor = new Vector(true, true, polje);
	IVector vektor1 = vektor.copy();
	assertArrayEquals(vektor.toArray(), vektor1.toArray(), 0.0);
    }
    
    @Test
    public void testNewInstance() {
	double[] polje = new double[10000];
	for (int i = 0; i < 10000; i++) {
	    polje[i] = i;
	}
	IVector vektor = new Vector(polje);
	for (int i = 0; i < 10000; i++) {
	    polje[i] = 0;
	}
	IVector vektor1 = vektor.newInstance(10000);
	assertArrayEquals(polje, vektor1.toArray(), 0.0);
	
	Throwable c = null;
	try {
	    vektor1 = vektor.newInstance(0);
	} catch (IllegalArgumentException e) {
	    c= e;
	}
	assertTrue(c!=null);
    }
    
    @Test
    public void testParseSimple() {
	StringBuilder builder = new StringBuilder();
	double[] polje = new double[10000];
	for(int i=0;i<10000;i++) {
	    polje[i]=i;
	}
	for(int i=0;i<10000;i++) {
	    builder.append(Integer.toString(i));
	    builder.append(" ");
	}
	IVector vektor = Vector.parseSimple(builder.toString());
	assertArrayEquals(polje, vektor.toArray(), 0.0);
    }
}
