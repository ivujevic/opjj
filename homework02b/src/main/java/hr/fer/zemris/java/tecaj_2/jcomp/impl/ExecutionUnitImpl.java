/**
 * Paket u kojem se nalaze implementacije sučelja:
 *  <code>Computer</code>, <code>ExecutionUnit</code> , <code>Memory</code>, <code>Registrers</code>
 */

package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;

/**
 * Razred koji implementira sučelje ExecutionUnit
 * 
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * Varijabla tipa <code>Instruction</code> u koju se sprema instrukcija koja
	 * se čita
	 */
	private Instruction instrukcija;

	/**
	 * Varijabla tipa <code>Object</code> u koju se sprema sadržaj registra PC
	 */
	private Object PC;

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);

		while (true) {
			PC = computer.getRegisters().getProgramCounter();
			instrukcija = (Instruction) computer.getMemory().getLocation(
					Integer.parseInt(PC.toString()));
			computer.getRegisters().setProgramCounter(
					Integer.parseInt(PC.toString()) + 1);
			if (instrukcija.execute(computer)) {
				break;
			}
		}
		return true;
	}
}
