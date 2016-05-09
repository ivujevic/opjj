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
 * <code>LOAD registar, adresa</code> u adresa -> registar
 */
public class InstrLoad implements Instruction {

	/**
	 * Varijabla u koju se sprema index registra naredbe <code>LOAD</code>
	 */
	private int indexRegistra;

	/**
	 * Varijabla u koju se sprema adresa u memoriji s koje se čita podatak
	 * naredbom <code>LOAD</code>
	 */
	private int adresaMemorije;

	/**
	 * Uzima parametre iz naredbe LOAD
	 * 
	 * @param arguments
	 *            Argumenti naredbe LOAD
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Očekuju se 2 parametra");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Krivi prvi parametar");
		}
		try {
			indexRegistra = ((Integer) arguments.get(0).getValue()).intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Krivi parametar");
		}
		try {
			adresaMemorije = ((Integer) arguments.get(1).getValue()).intValue();
		} catch (Throwable e) {
			throw new ClassCastException("Kriva adresa u memoriji");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getMemory().getLocation(adresaMemorije);
		computer.getRegisters().setRegisterValue(this.indexRegistra, value1);
		return false;
	}
}
