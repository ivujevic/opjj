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
 * <code>ADD registar1, registar2, registar3</code> registar1 = registar2 +
 * registar3
 * 
 */
public class InstrAdd implements Instruction {
	/**
	 * Varijabla u koju se sprema index prvog registra u naredbi
	 * <code>ADD</code>
	 */
	private int indexRegistra1;

	/**
	 * Varijabla u koju se sprema index drugog registra u naredbi
	 * <code>ADD</code>
	 */
	private int indexRegistra2;

	/**
	 * Varijabla u koju se sprema index trećeg registra u naredbi
	 * <code>ADD</code>
	 */
	private int indexRegistra3;

	/**
	 * Uzima arguemnte iz naredbe ADD
	 * 
	 * @param arguments
	 */
	public InstrAdd(List<InstructionArgument> arguments) {

		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Očekuju se 3 parametra");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Krivi prvi parametar");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Krivi drugi parametar");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Krivi treći parametar");
		}
		try {
			this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi prvi parametar");
		}

		try {
			this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi drugi parametar");
		}

		try {
			this.indexRegistra3 = ((Integer) arguments.get(2).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi treći parametar");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);
		try {
			computer.getRegisters().setRegisterValue(
					indexRegistra1,
					(Integer.parseInt(value1.toString()) + Integer
							.parseInt(value2.toString())));
		} catch (Throwable e) {
			throw new ClassCastException("Zadani parametar nije broj");
		}
		return false;
	}
}
