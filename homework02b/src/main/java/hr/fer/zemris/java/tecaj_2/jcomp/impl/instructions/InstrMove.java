package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

public class InstrMove implements Instruction {

	/**
	 * Varijabla u koju se sprema index prvog registra naredbe <code>MOVE</code>
	 */
	private int indexRegistra1;

	/**
	 * Varijabla u koju se sprema index drugog registra naredbe
	 * <code>MOVE</code>
	 */
	private int indexRegistra2;

	/**
	 * 
	 * @param arguments
	 *            Argumenti naredbe <code>MOVE</code>
	 */

	public InstrMove(List<InstructionArgument> arguments) {

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
		Object temp;
		Object value1 = computer.getRegisters().getRegisterValue(
				this.indexRegistra1);
		Object value2 = computer.getRegisters().getRegisterValue(
				this.indexRegistra2);
		temp = value1;
		value1 = value2;
		value2 = value1;
		computer.getRegisters().setRegisterValue(indexRegistra1, value1);
		computer.getRegisters().setRegisterValue(indexRegistra2, value2);
		return false;
	}
}
