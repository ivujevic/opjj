/**
 * 
 */
package hr.fer.zemris.java.hw11.pools;

/**
 * Razred koja predstavlja jedan bend
 * 
 * @author Ivan
 * 
 */
public class PoolOption {

	/**
	 * id elementa ankete.
	 */
	long id;

	/**
	 * naslov elementa ankete.
	 */
	String title;

	/**
	 * link.
	 */
	String optionLink;

	/**
	 * brojač glasova.
	 */
	int votesCount;

	/**
	 * Konstruktor.
	 * 
	 * @param id
	 *            id
	 * @param title
	 *            naslov
	 * @param optionLink
	 *            link
	 * @param votesCount
	 *            brojač glasova
	 */
	public PoolOption(long id, String title, String optionLink, int votesCount) {
		super();
		this.id = id;
		this.title = title;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}

	/**
	 * vraća id.
	 * 
	 * @return id elementa
	 */
	public long getId() {
		return id;
	}

	/**
	 * vraća naslov.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * vraća link.
	 * 
	 * @return link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * vraća broj glasova.
	 * 
	 * @return broj glasova
	 */
	public int getVotesCount() {
		return votesCount;
	}

}
