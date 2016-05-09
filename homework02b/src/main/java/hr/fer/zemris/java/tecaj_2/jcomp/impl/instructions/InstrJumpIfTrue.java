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
 * <code>JUMPIFTRUE adresa</code> adresa -> PC ako je zastavica postavljena
 * 
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * Varijabla u koju se sprema lokacija na koju skače naredba
	 * <code>JUMPIFTRUE</code>
	 */
	private int lokacija;

	/**
	 * 
	 * @param arguments
	 *            Lokacija na koju se skače
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {

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
		Object value2 = computer.getRegisters().getFlag();
		try {
			if (Boolean.parseBoolean(value2.toString())) {
				computer.getRegisters().setProgramCounter(this.lokacija);
			}
		} catch (Throwable e) {
			throw new ClassCastException("Kriva boolean vrijednost");
		}
		return false;
	}
}
