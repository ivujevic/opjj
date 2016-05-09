import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.MemoryImpl;

import org.junit.Test;


public class MemoryImplTest {

	@Test
	public void testSetGetLocation() {
		Memory memorija = new MemoryImpl(10);
		memorija.setLocation(2, 5);
		assertEquals(5, memorija.getLocation(2));
	}

}
