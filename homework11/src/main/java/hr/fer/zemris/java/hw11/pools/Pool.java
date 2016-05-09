/**
 * 
 */
package hr.fer.zemris.java.hw11.pools;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja jednu anketu.
 * 
 * @author Ivan
 * 
 */
public class Pool {

	/**
	 * Id pod kojim je anketa pohranjena u bazu podataka.
	 */
	long id;

	/**
	 * Naslov koji se prikazuje za anketu.
	 */
	String title;

	/**
	 * Poruka koja se prikazuje prilikom glasanja.
	 */
	String message;

	/**
	 * Lista u koju su spremljeni mogući izbori u anketi.
	 */
	List<PoolOption> elements = new ArrayList<>();

	/**
	 * Konstruktor.
	 * 
	 * @param id
	 *            id ankete
	 * @param title
	 *            naslov ankete
	 * @param message
	 *            poruka koja se prikazuje za anketu
	 * @param elements
	 *            elementi ankete
	 */
	public Pool(long id, String title, String message, List<PoolOption> elements) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.elements = elements;
	}

	/**
	 * Vraća id ankete.
	 * 
	 * @return id ankekte
	 */
	public long getId() {
		return id;
	}

	/**
	 * Vraća naslov ankete.
	 * 
	 * @return naslov ankekte
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Vraća poruku ankete.
	 * 
	 * @return poruku ankekte
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Vraća listu elemenata ankete.
	 * 
	 * @return lista elemenata ankekte
	 */
	public List<PoolOption> getElements() {
		return elements;
	}

}
