import static org.junit.Assert.*;
import hr.fer.zemris.java.graphics.shapes.Triangle;

import org.junit.Test;


public class TriangleTest {

	@Test
	public void testContainsPoint() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(true, tri.containsPoint(1, 1));
		assertEquals(false, tri.containsPoint(54, 45));
	}
	
	@Test
	public void testGetX1() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(0, tri.getX1());
	}
	
	@Test
	public void testGetX2() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(4, tri.getX2());
	}
	
	@Test
	public void testGetX3() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(0, tri.getX3());
	}
	
	@Test
	public void testGetY1() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(0, tri.getY1());
	}
	
	@Test
	public void testGetY2() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(0, tri.getY2());
	}
	
	@Test
	public void testGetY3() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		assertEquals(3, tri.getY3());
	}
	
	@Test
	public void testSetX1() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setX1(1);
		assertEquals(1, tri.getX1());
	}
	
	@Test
	public void testSetX2() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setX2(5);
		assertEquals(5, tri.getX2());
	}
	
	@Test
	public void testSetX3() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setX3(3);
		assertEquals(3, tri.getX3());
	}
	
	@Test
	public void tesSetY1() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setY1(5);
		assertEquals(5, tri.getY1());
	}
	
	@Test
	public void testSetY2() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setY2(10);
		assertEquals(10, tri.getY2());
	}
	
	@Test
	public void testSetY3() {
		Triangle tri = new Triangle(0, 0, 4, 0, 0, 3);
		tri.setY3(5);
		assertEquals(5, tri.getY3());
	}
}
