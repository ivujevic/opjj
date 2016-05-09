/**
 * Paket u kojem se nalaze implementirane instrukcije
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Razred koji implementira sučelje Instruction Naredba
 * <code>TestEquals registar1, registar2</code> uspoređje sadržaj registara
 * Usporedbu obavlja uspoređujući stringove
 * 
 */
public class InstrTestEquals implements Instruction {

	/**
	 * Index prvog registra naredbe <code>TESTEQUALS</code>
	 */
	private int indexRegistra1;

	/**
	 * Index drugog registra naredbe <code>TESTEQUALS</code>
	 */
	private int indexRegistra2;

	/**
	 * 
	 * @param arguments
	 *            Arguementi naredbe TestEquals
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Očekuje se 2 parametra");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Krivi prvi parametar");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Krivi drugi parametar");
		}
		try {
			this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
					.intValue();
			this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
		}

	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(
				this.indexRegistra1);
		Object value2 = computer.getRegisters().getRegisterValue(
				this.indexRegistra2);
		try {
			if (Integer.parseInt(value1.toString()) == Integer.parseInt(value2
					.toString())) {
				computer.getRegisters().setFlag(true);
			}
		} catch (Throwable e) {
			throw new ClassCastException("Pohranjena je kriva vrijednost");
		}
		return false;
	}
}
