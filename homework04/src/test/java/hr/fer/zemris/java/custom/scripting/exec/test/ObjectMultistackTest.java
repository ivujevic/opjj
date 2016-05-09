/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec.test;

import static org.junit.Assert.*;
import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import org.junit.Test;

/**
 * @author Ivan
 * 
 */
public class ObjectMultistackTest {

	@Test(expected = IllegalAccessError.class)
	public void testObjectMultistackPushPop() {

		ObjectMultistack stack = new ObjectMultistack();

		for(int i=0;i<10;i++) {
			stack.push("prvi", new ValueWrapper(i));
		}
		
		assertTrue("Greška u dodavanju na prvi stog",!stack.isEmpty("prvi"));
		for(int i=10;i<20;i++) {
			stack.push("drugi", new ValueWrapper(i));
		}
		
		assertTrue("Greška u dodavanju na drugi stog",!stack.isEmpty("drugi"));
		
		int[] arrayStack = new int[10];
		int[] arrayTest = new int[10];
		for(int i=0;i<10;i++) {
			arrayTest[i] =i;
		}
		
		for(int i=0;i<10;i++) {
			arrayStack[9-i] = (Integer) stack.pop("prvi").getValue();
		}
		
		assertArrayEquals("Greška pri skidanju elemenata s prvog stoga",arrayTest, arrayStack);
		
		for(int i=0;i<10;i++) {
			arrayTest[i] =10+i;
		}
		
		for(int i=0;i<10;i++) {
			arrayStack[9-i] = (Integer) stack.pop("drugi").getValue();
		}
		assertTrue(stack.isEmpty("prvi"));
		stack.pop("prvi");
		assertArrayEquals("Greška pri skidanju elemenata s drugog stoga",arrayTest, arrayStack);
	}
	
	@Test(expected = IllegalAccessError.class)
	
	public void testPeek() {
		
		ObjectMultistack stack = new ObjectMultistack();
		for(int i=0;i<1000;i++) {
			stack.push("prvi", new ValueWrapper(i));
		}
		int result = (Integer) stack.peek("prvi").getValue();
		assertEquals("Pogreška u narebi peek", 999,result);
		for(int i=0;i<1000;i++) {
			stack.pop("prvi");
		}
		stack.peek("prvi");
		stack.peek("drugi");
	}
}
