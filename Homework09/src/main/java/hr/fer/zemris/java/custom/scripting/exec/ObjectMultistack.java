/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred koji predstavlja strukturu koja nalikuje stogu na koji su vrijednosti spremljene uz odgovarajući
 * ključ. Za pohranu podatka koristi <code>Map</code> u kojem je <code>key</code> String koji odgovara
 * željenom ključu, a <code>value</code> je reference na <code>MultistackEntry</code> koji ima funkcije stoga.
 * 
 */
public class ObjectMultistack {

	Map<String, MultistackEntry> mapa = new HashMap<>();

	/**
	 * Metoda koja dodaje element na stog pojedinog ključa.
	 * 
	 * @param name
	 *            Ključ koji određuje stog na koji se dodaje
	 * @param valueWrapper
	 *            Vrijednost koja se dodaje na stog.
	 */
	public final void push(final String name, final ValueWrapper valueWrapper) {

		if (!mapa.containsKey(name)) {
			mapa.put(name, new MultistackEntry(valueWrapper));
		}
		else {
			mapa.put(name, mapa.get(name).push(valueWrapper));
		}
	}

	/**
	 * Metoda koja skida element sa stoga zadanog ključa.
	 * 
	 * @param name
	 *            Ključ s čijeg se stoga vrijednos skida
	 * @return Vrijednost koja je skinuta s vrha stoga
	 */
	public final ValueWrapper pop(final String name) {
		try {
			if (mapa.get(name).size() == 0) {
				throw new IllegalAccessError("Na stogu nema podataka!");
			}
			else {
				return mapa.get(name).pop();
			}
		} catch (Throwable e) {
			throw new IllegalAccessError("Ne postoji takav stog!");
		}

	}

	/**
	 * Metoda koja vraća posljednji dodani element na stog, ali ga ne skida sa stoga.
	 * 
	 * @param name
	 *            Ključ s čijeg se stoga uzima element
	 * @return Element koji je posljednji dodan na stog
	 */
	public final ValueWrapper peek(final String name) {
		try {
			if (mapa.get(name).size() == 0) {
				throw new IllegalAccessError("Na stogu nema podataka!");
			}
			else {
				return mapa.get(name).peek();
			}
		} catch (Throwable e) {
			throw new IllegalAccessError("Ne postoji takav stog");
		}
	}

	/**
	 * Metoda koja provjerava je li stog određenog ključa prazan.
	 * 
	 * @param name
	 *            Ključ za koji se gleda stanje stoga
	 * @return <code>true</code> ako je stog prazan, inače <code>false</code>
	 */
	public final boolean isEmpty(final String name) {
		return mapa.get(name).size() == 0;
	}
}
