/**
 * Paket u kojem se nalaze implementacije sučelja:
 *  <code>Computer</code>, <code>ExecutionUnit</code> , <code>Memory</code>, <code>Registrers</code>
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

/**
 * 
 * Razred koji implementira sučelje Memory
 * 
 */
public class MemoryImpl implements Memory {

	/**
	 * Polje tipa <code>Object</code> koje predstavlja memoriju računala
	 */
	protected static Object[] memorija;

	/**
	 * Kreira polje tipa <code>Object</code> koji predstavljaju memoriju
	 * 
	 * @param size
	 *            Veličina memorije
	 */
	public MemoryImpl(int size) {
		memorija = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		try {
			memorija[location] = value;
		} catch (Throwable e) {
			System.out.println("Nevaljala adresa za pisanje u memoriju");
			System.exit(1);
		}

	}

	@Override
	public Object getLocation(int location) {
		try {
			return memorija[location];
		} catch (Throwable e) {
			System.out.println("Ne valjala adresa za dohvat iz memorije");
			System.exit(1);
		}
		return null;
	}

}
