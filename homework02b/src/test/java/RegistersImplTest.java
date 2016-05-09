import static org.junit.Assert.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.RegistersImpl;

import org.junit.Test;


public class RegistersImplTest {

	@Test
	public void testSetGetRegisterValue() {
		Registers registar = new RegistersImpl(15);
		registar.setRegisterValue(2, 5);
		assertEquals(5, registar.getRegisterValue(2));
	}

	@Test
	public void testSetGetProgramCounter() {
		Registers registar = new RegistersImpl(15);
		registar.setProgramCounter(5);
		assertEquals(5, registar.getProgramCounter());
	}
	
	@Test
	public void testIncrementProgramCounter() {
		Registers registar = new RegistersImpl(15);
		registar.setProgramCounter(5);
		registar.incrementProgramCounter();
		assertEquals(6, registar.getProgramCounter());
	}
	
	@Test
	public void testSetGetFlag() {
		Registers registar = new RegistersImpl(15);
		registar.setFlag(true);
		assertEquals(true, registar.getFlag());
		registar.setFlag(false);
		assertEquals(false, registar.getFlag());
	}
}
