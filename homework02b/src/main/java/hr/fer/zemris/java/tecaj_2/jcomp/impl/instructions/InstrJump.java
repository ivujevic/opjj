/**
 * Paket u kojem se nalaze implementirane instrukcije
 */

package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Razred koji implementira sučelje Instruction Naredba <code>JUMP adresa</code>
 * adresa -> PC
 * 
 */
public class InstrJump implements Instruction {

	/**
	 * Varijabla u koju se sprema lokacija na koju se skače naredbom
	 * <code>JUMP</code>
	 */
	private int lokacija;

	/**
	 * @param arguments
	 *            Lokacija na koju se skače
	 */
	public InstrJump(List<InstructionArgument> arguments) {

		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Očekuje se 1 parametar");
		}
		try {
			this.lokacija = ((Integer) arguments.get(0).getValue()).intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(this.lokacija);
		return false;
	}
}
