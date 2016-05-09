import static org.junit.Assert.*;
import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;

import org.junit.Test;


public class PravokutniLikoviTest {

	@Test
	public void TestGetX() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 20);
		Square square = new Square(10, 10, 5);
		assertEquals(0, rectangle.getX());
		assertEquals(10, square.getX());
	}

	@Test
	public void TestGetY() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 20);
		Square square = new Square(10, 10, 5);
		assertEquals(0, rectangle.getY());
		assertEquals(10, square.getY());
	}
	
	@Test
	public void TestSetX() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 20);
		Square square = new Square(10, 10, 5);
		rectangle.setX(10);
		square.setX(0);
		assertEquals(10, rectangle.getX());
		assertEquals(0, square.getX());
	}
	
	@Test
	public void TestSetY() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 20);
		Square square = new Square(10, 10, 5);
		rectangle.setY(10);
		square.setY(0);
		assertEquals(10, rectangle.getY());
		assertEquals(0, square.getY());
	}
	
	@Test
	public void TestContainsPoint() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 20);
		Square square = new Square(10, 10, 5);
		assertEquals(true, rectangle.containsPoint(0, 0));
		assertEquals(true, square.containsPoint(10, 10));
		assertEquals(false, rectangle.containsPoint(-5, -5));
		assertEquals(false, square.containsPoint(-5, -7));
		assertEquals(false, rectangle.containsPoint(500, 500));
		assertEquals(false, square.containsPoint(500, 500));
	}
	
	@Test
	public void TestDraw() {
		BWRaster raster = new BWRasterMem(100, 100);
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
		Square square = new Square(0, 10, 10);
		rectangle.draw(raster);
		square.draw(raster);
		assertEquals(true, raster.isTurnedOn(10, 10));
		assertEquals(false, raster.isTurnedOn(80, 80));
		
	}
}
