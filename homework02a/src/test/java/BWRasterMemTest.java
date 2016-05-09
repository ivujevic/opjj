import static org.junit.Assert.*;
import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;

import org.junit.Test;


public class BWRasterMemTest {

	@Test
	public void testBWRasterMem() {
		
		BWRaster raster = new BWRasterMem(40, 80);
		assertEquals(40, raster.getWidth());
		assertEquals(80, raster.getHeight());
	}
	
	@Test
	public void testGetWidth() {
		BWRaster raster = new BWRasterMem(40, 80);
		assertEquals(40, raster.getWidth());
	}

	@Test
	public void testGetHeight() {
		BWRaster raster = new BWRasterMem(40, 80);
		assertEquals(80, raster.getHeight());
	}
	
	@Test
	public void testClear() {
		BWRaster raster = new BWRasterMem(1000, 4524);
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				raster.turnOn(j, i);
			}
		}
		raster.clear();
		
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				assertEquals(false, raster.isTurnedOn(j, i));
			}
		}
		
	}
	
	@Test
	public void testTurnOn() {
		BWRaster raster = new BWRasterMem(1000, 4524);
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				raster.turnOn(j, i);
			}
		}
		
		raster.enableFlipMode();
		
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				raster.turnOn(j, i);
			}
		}
		raster.disableFlipMode();
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				assertEquals(false, raster.isTurnedOn(j, i));
			}
		}
	}
	
	@Test
	public void testTurnOff() {
		BWRaster raster = new BWRasterMem(1000, 4524);
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				raster.turnOn(j, i);
			}
		}
		
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				raster.turnOff(j, i);
			}
		}
		
		for (int j = 0; j < 1000; j++) {
			for(int i = 0; i < 4524; i++) {
				assertEquals(false, raster.isTurnedOn(j, i));
			}
		}
		
	}
}
