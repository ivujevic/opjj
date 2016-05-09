/**
 * Paket u kojem se nalaze implementacije sučelja:
 *  <code>Computer</code>, <code>ExecutionUnit</code> , <code>Memory</code>, <code>Registrers</code>
 */

package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Razred koji implementira sučelje Registers
 * 
 */
public class RegistersImpl implements Registers {

	/**
	 * Polje tipa <code>Object</code> koje predstavlja registre računala
	 */
	protected static Object[] registri;

	/**
	 * Kreira polje tipa <code>object</code> koje predstavlja registre
	 * 
	 * @param regLen
	 *            Veličina skupa registara
	 */
	public RegistersImpl(int regLen) {
		registri = new Object[regLen + 2];
	}

	@Override
	public Object getRegisterValue(int index) {
		try {
			return registri[index];
		} catch (Throwable e) {
			System.out.println("Traženi registar ne postoji");
			System.exit(1);
		}
		return null;
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		try {
			registri[index] = value;
		} catch (Throwable e) {
			System.out.println("Traženi registar ne postoji");
			System.exit(1);
		}

	}

	@Override
	public int getProgramCounter() {
		return Integer.parseInt(registri[registri.length - 1].toString());
	}

	@Override
	public void setProgramCounter(int value) {
		registri[registri.length - 1] = value;

	}

	@Override
	public void incrementProgramCounter() {
		registri[registri.length - 1] = Integer
				.parseInt(registri[registri.length - 1].toString()) + 1;

	}

	@Override
	public boolean getFlag() {
		return Boolean.parseBoolean(registri[registri.length - 2].toString());
	}

	@Override
	public void setFlag(boolean value) {
		registri[registri.length - 2] = value;

	}
}
