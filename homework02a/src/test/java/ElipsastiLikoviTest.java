import static org.junit.Assert.*;
import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;

import org.junit.Test;


public class ElipsastiLikoviTest {

	@Test
	public void testGetX() {
		Ellipse elipsa = new Ellipse(100, 100, 40, 85);
		Circle circle = new Circle(5, 5, 10);
		assertEquals(100, elipsa.getX());
		assertEquals(5, circle.getX());
	}
	
	@Test
	public void testGetY() {
		Ellipse elipsa = new Ellipse(100, 100, 45, 321);
		Circle circle = new Circle(5, 5, 10);
		assertEquals(100, elipsa.getY());
		assertEquals(5, circle.getY());
	}

	@Test
	public void testSetX() {
		Ellipse elipsa = new Ellipse(100, 100, 45, 321);
		Circle circle = new Circle(5, 5, 10);
		elipsa.setX(20);
		assertEquals(20, elipsa.getX());
		circle.setX(54);
		assertEquals(54, circle.getX());
	}
	
	@Test
	public void testSetY() {
		Ellipse elipsa = new Ellipse(100, 100, 45, 321);
		Circle circle = new Circle(5, 5, 10);
		elipsa.setY(12);
		assertEquals(12, elipsa.getY());
		circle.setY(50);
		assertEquals(50, circle.getY());
	}
	
	@Test
	public void testContainsPoint() {
		Ellipse elipsa = new Ellipse(100, 100, 45, 321);
		Circle circle = new Circle(5, 5, 10);
		assertEquals(true, elipsa.containsPoint(100, 100));
		assertEquals(false, elipsa.containsPoint(-5, -5));
		assertEquals(false, circle.containsPoint(5, -5));
		assertEquals(false, elipsa.containsPoint(-5, 5));
		assertEquals(false, circle.containsPoint(1456, 64564));
	}
	
	@Test
	public void testDraw() {
		BWRaster raster = new BWRasterMem(100, 100);
		Ellipse elipsa = new Ellipse(10, 5, 12, 16);
		Circle circle = new Circle(10, 10, 15);
		elipsa.draw(raster);
		circle.draw(raster);
		assertEquals(true, raster.isTurnedOn(10, 5));
		assertEquals(false, raster.isTurnedOn(100, 100));
	}
}
