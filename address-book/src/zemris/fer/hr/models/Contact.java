package zemris.fer.hr.models;

import android.graphics.Bitmap;

/**
 * Razred koji predstavlja jedan zapis iz liste kontakata. Kontakt ima ime, broj
 * telefone, email, sliku te link na facebook profil
 * 
 * @author Vujevic
 * 
 */
public class Contact {

	/**
	 * Ime kontakta.
	 */
	private String name;

	/**
	 * Broj telefona kontakta.
	 */
	private String phone;

	/**
	 * Email adresa kontakta.
	 */
	private String email;

	/**
	 * Slika kontakta.
	 */
	private Bitmap image;

	/**
	 * Link na facebook profil kontakta.
	 */
	private String facebookLink;
	
	/**
	 * Bilješka za kontakt
	 */
	private String note;
	
	/**
	 * Prazni konstruktor.
	 */
	public Contact(){
		
	}

	/**
	 * Konstruktor razreda sa svim parametrima koje instanca razreda mora imati.
	 * 
	 * @param name
	 *            ime korisnika.
	 * @param phone
	 *            broj telefona
	 * @param email
	 *            email adresa
	 * @param image
	 * 
	 *            slika
	 * @param facebookLink
	 *            facebook link.
	 */
	public Contact(String name, String phone, String email, Bitmap image,
			String facebookLink) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.image = image;
		this.facebookLink = facebookLink;
	}

	/**
	 * Metoda koja vraæa ime kontakta.
	 * @return ime korisnika
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metoda koja postavlja ime kontakta na vrijednost koja joj je predana kao parametar.
	 * @param name Novo ime kontkakta.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Metoda koja vraæa broj telefona kontakta.
	 * @return broj teleofona kontakta
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Metoda koja postavlja broj telefona kontakta na vrijednost koja joj je predana kao parametar.
	 * @param phone Novi broj telefona.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Metoda koja vraæa email adresu kontakta.
	 * @return email adresa kontakta.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda koja postavlja email adresa kontakta na vrijednost koja joj je predana kao parametar.
	 * @param email Nova email adresa.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda koja vraæa sliku kontakta.
	 * @return slika kontakta.
	 */
	public Bitmap getImage() {
		return image;
	}

	/**
	 * Metoda koja postavlja sliku kontakta na novu sliku koja je predana kao parametar.
	 * @param image Nova slika kontakta.
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}

	/**
	 * Metoda koja vraæa link na kontaktov facebook profil.
	 * @return link na facebook profil.
	 */
	public String getFacebookLink() {
		return facebookLink;
	}
	
	
	/**
	 * Metoda koja vraæa kontaktovu bilješku.
	 * @return bilješka
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Metoda koja postavlja biljšku na novu bilješku koja je predana kao parametar.
	 * @param note Nova bilješka kontakta.
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Metoda koja postavlja facebook link na novu vrijednost koja je predana kao parametar.
	 * @param facebookLink Novi link na facebook profil.
	 */
	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	
}
