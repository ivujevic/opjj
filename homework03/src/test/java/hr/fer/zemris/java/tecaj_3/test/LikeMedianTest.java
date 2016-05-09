package hr.fer.zemris.java.tecaj_3.test;

import static org.junit.Assert.*;
import junit.framework.Assert;
import hr.fer.zemris.java.tecaj_3.LikeMedian;

import org.junit.Test;

public class LikeMedianTest {

	@Test
	public void testMedianOfIntegers() {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();

		for (int i = 0; i < 10000; i++) {
			likeMedian.add(i);
		}
		int result = likeMedian.get();
		assertEquals(4999, result);
	}
	
	@Test
	public void testMedianofIntegersNeparanBrojElemenata() {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();

		for (int i = 0; i < 9999; i++) {
			likeMedian.add(i);
		}
		int result = likeMedian.get();
		assertEquals(4999, result);
	}
}
