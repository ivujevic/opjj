/**
 * Paket u kojem se nalaze implementirane instrukcije
 */

package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Razred koji implementira sučelje Instruction Naredba <code>HALT</code>
 * prekida rad procesora
 */

public class InstrHalt implements Instruction {

	/**
	 * 
	 * @param arguments
	 *            Ne očekuje nikoje parametre
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Parametri se ne očekuju");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}
}
