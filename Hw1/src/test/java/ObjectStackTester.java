import static org.junit.Assert.*;
import hr.fer.zemris.java.custom.collections.ObjectStack;

import org.junit.Test;


public class ObjectStackTester {
	private ObjectStack stog;

	@Test
	public void testIsEmpty() {
		stog= new ObjectStack();
		stog.push(new Integer(2));
		stog.pop();
		assertTrue(stog.isEmpty());
	}

	@Test
	public void testSize() {
		stog= new ObjectStack();
		for(int i=0; i < 20 ;i++) {
			stog.push(i);
		}
		assertEquals(stog.size(),20);
	}

	@Test
	public void testClear() {
		stog= new ObjectStack();
		for(int i=0; i < 20 ;i++) {
			stog.push(i);
		}
		stog.clear();
		assertTrue(stog.isEmpty());
	}

	@Test
	public void testPushNull() {
		stog= new ObjectStack();
		Throwable uhvati = null;
		
		try {
			stog.push(null);
		} catch (Throwable t) {
			uhvati = t;
		}
		
		assertNotNull(uhvati);
	}

	@Test
	public void testPopPrazan() {
		stog= new ObjectStack();
		stog.push(12);
		stog.pop();
		Throwable uhvati = null;
		
		try {
			stog.pop();
		} catch (Throwable t) {
			uhvati = t;
		}
		
		assertNotNull(uhvati);
	}

	@Test
	public void testPeek() {
		stog= new ObjectStack();
		stog.push(12);
		stog.push(13);
		stog.push(24);
		assertEquals(24, stog.peek());
	}

}
