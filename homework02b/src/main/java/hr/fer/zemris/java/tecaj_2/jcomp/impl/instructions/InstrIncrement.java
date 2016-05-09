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
 * <code>INCREMENT registar</code> registar++
 * 
 */

public class InstrIncrement implements Instruction {

	/**
	 * Varijabla u koju se sprema index registra naredbe <code>INCREMENT</code>
	 */
	private int indexRegistra;

	/**
	 * @param arguments
	 *            Registar kojem se povećava vrijednost za 1
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {

		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Očekuje se 1 parametar");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Krivi prvi parametar");
		}

		try {
			this.indexRegistra = ((Integer) arguments.get(0).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
		}
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra);
		try {
			computer.getRegisters().setRegisterValue(this.indexRegistra,
					Integer.parseInt(value1.toString()) + 1);
		} catch (Throwable e) {
			throw new ClassCastException("U registru mora biti spremljen broj");
		}
		return false;
	}
}
