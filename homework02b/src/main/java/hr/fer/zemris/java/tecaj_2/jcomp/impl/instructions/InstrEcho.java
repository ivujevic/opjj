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
 * <code>ECHO registar</code> ispisuje na zaslon sadržaj <code>registar</code>
 * 
 */
public class InstrEcho implements Instruction {

	/**
	 * Varijabla u koju se sprema index registra naredbe <code>ECHO</code>
	 */
	private int indexRegistra;

	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Očekuju se 1 parametar");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Krivi prvi parametar");
		}
		try {
			indexRegistra = ((Integer) arguments.get(0).getValue()).intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(
				this.indexRegistra);
		System.out.print(value1);
		return false;
	}
}