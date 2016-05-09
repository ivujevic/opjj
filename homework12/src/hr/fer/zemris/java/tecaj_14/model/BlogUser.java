package hr.fer.zemris.java.tecaj_14.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Razred u kojem se implementira korisnik bloga.
 * 
 * @author Ivan
 * 
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String eMail;
	private String passwordHash;
	private List<BlogEntry> userEntries;

	public BlogUser() {
	}

	/**
	 * Konstruktor koji stvara novog korisnika.
	 * 
	 * @param nick
	 *            korisničko ime korisnika.
	 * @param password
	 *            lozinka korisnika
	 * @param firstName
	 *            ime korisnika
	 * @param lastName
	 *            prezime korisnika.
	 * @param eMail
	 *            email adresa korisnika.
	 */
	public BlogUser(String nick, String password, String firstName, String lastName, String eMail) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.eMail = eMail;
		this.passwordHash = calculateHash(password);
	}

	/**
	 * Vraća id korisnika.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Postavlja id korisnika.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Vraća ime korisnika.
	 * 
	 * @return ime korisnika.
	 */
	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Postavlja ime korisnika.
	 * 
	 * @param firstName
	 *            ime korisnika.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Vraća prezime korisnika.
	 * 
	 * @return prezime korisnika.
	 */
	@Column(length = 40, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Postavlja przime korisnika.
	 * 
	 * @param lastName
	 *            prezime korisnika.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Vraća korisničko ime korisnika.
	 * 
	 * @return korisničko ime.
	 */
	@Column(length = 20, nullable = false)
	public String getNick() {
		return nick;
	}

	/**
	 * postavlja korisničko ime korisnika.
	 * 
	 * @param nick
	 *            korisničko ime.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Vraća email korisnika.
	 * 
	 * @return email korisnika
	 */
	@Column(nullable = false, length = 25)
	public String geteMail() {
		return eMail;
	}

	/**
	 * postavlja email korisnika .
	 * 
	 * @param eMail
	 *            email korisnika.
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * Vraća lozinku korisnika (njenu hash vrijednost).
	 * 
	 * @return lozinka korisnika.
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * postavlja lozinku korisnika.
	 * 
	 * @param password
	 *            lozinka korisnika.
	 */
	public void setPasswordHash(String password) {
		this.passwordHash = password;
	}

	/**
	 * Vraća listu zapisa koje je kreirao korisnika.
	 * 
	 * @return lista zapisa korisnika.
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	@OrderBy("createdAt")
	public List<BlogEntry> getUserEntries() {
		return userEntries;
	}

	/**
	 * Postavlja listu zapisa koje je kreirao korisnika.
	 * 
	 * @param userEntries
	 *            lista zapisa.
	 */
	public void setUserEntries(List<BlogEntry> userEntries) {
		this.userEntries = userEntries;
	}

	/**
	 * Dodaje zapis u listu zapisa.
	 * 
	 * @param userEntry
	 *            novi zapis.
	 */
	public void addUserEntry(BlogEntry userEntry) {
		userEntries.add(userEntry);
	}

	/**
	 * Briše zapis iz liste zapisa.
	 * 
	 * @param entry
	 *            zapis koji se želi izbrisati.
	 */
	public void removeUserEntry(BlogEntry entry) {
		userEntries.remove(entry);
	}

	/**
	 * Metoda koja računa hash vrijednost lozinke
	 * 
	 * @param password
	 *            lozinka
	 * @return hash vrijednost lozinke.
	 */
	public String calculateHash(String password) {
		MessageDigest passwordDigest = null;
		try {
			passwordDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		passwordDigest.update(password.getBytes());
		byte[] digest = passwordDigest.digest();
		return new String(digest);
	}

	/**
	 * Metoda koja uspoređuje predanu šifru sa šifrom korisnika.
	 * 
	 * @param password
	 *            šifra koja se uspoređuje sa šifrom korisnika.
	 * @return true ako su iste, inače false.
	 */
	public boolean checkPasswordForUser(String password) {
		if (passwordHash.equals(calculateHash(password)))
			return true;
		return false;
	}
}
