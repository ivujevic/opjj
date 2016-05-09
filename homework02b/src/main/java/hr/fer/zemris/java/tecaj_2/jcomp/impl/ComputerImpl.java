/**
 * Paket u kojem se nalaze implementacije sučelja:
 *  <code>Computer</code>, <code>ExecutionUnit</code> , <code>Memory</code>, <code>Registrers</code>
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Razred koji implementira sučelje Computer "Stvara" registre i memoriju te ih
 * vraća
 */
public class ComputerImpl implements Computer {

	/**
	 * Varijabla tipa <code>Registers</code> koja prestavlja registre u računalu
	 */
	protected static Registers registri;

	/**
	 * Varijabla tipa <code>Memory</code> koja prestavlja memoriju računala
	 */
	protected static Memory memorija;

	/**
	 * Stvara memoriju i registre
	 * 
	 * @param brMemorijskihLokacija
	 *            Veličina memorije
	 * @param brRegistara
	 *            Veličina skupa registara
	 */
	public ComputerImpl(int brMemorijskihLokacija, int brRegistara) {
		registri = new RegistersImpl(brRegistara);
		memorija = new MemoryImpl(brMemorijskihLokacija);
		registri.setFlag(false);
	}

	@Override
	public Registers getRegisters() {
		return registri;
	}

	@Override
	public Memory getMemory() {
		return memorija;
	}

}
