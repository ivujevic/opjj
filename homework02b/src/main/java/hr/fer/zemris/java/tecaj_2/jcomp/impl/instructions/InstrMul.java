/**
 * Paket u kojem se nalaze implementirane instrukcije
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Rzred koji implementira sučelje Instruction NAREDBA
 * <code>MUL registar1, registar2, registar3</code> registar1 = registar2 *
 * registar3
 */
public class InstrMul implements Instruction {

	/**
	 * Varijabla u koju se sprema indeks prvog registra naredbe <code>MUL</code>
	 */
	private int indexRegistra1;

	/**
	 * Varijabla u koju se sprema indeks drugog registra naredbe
	 * <code>MUK</code>
	 */
	private int indexRegistra2;

	/**
	 * Varijabla u koju se sprema indeks trećeg registra naredbe
	 * <code>MUL</code>
	 */
	private int indexRegistra3;

	/**
	 * Uzima parametre iz naredbe MUL
	 * 
	 * @param arguments
	 *            Argumenti naredbe MUL
	 */
	public InstrMul(List<InstructionArgument> arguments) {
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
			this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
					.intValue();
			this.indexRegistra3 = ((Integer) arguments.get(2).getValue())
					.intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
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
					(Integer.parseInt(value1.toString()) * Integer
							.parseInt(value2.toString())));
		} catch (Throwable e) {
			throw new ClassCastException("Vrijednost u registru mora biti broj");
		}
		return false;
	}

}
