/**
 * Paket u kojem se nalazi razred s metodom <code>main</code>
 */
package hr.fer.zemris.java.tecaj_2.jcomp.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionCreator;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

/**
 * Razred koji sadrži metodu <code>main</code> zadaje iz koje datoteke čitati
 * kod
 */
public class test {
	public static void main(String[] args) {
		Computer comp = new ComputerImpl(256, 16);
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions");

		try {
			ProgramParser.parse("examples/asmProgram2.txt", comp, creator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);
	}
}
