package hr.fer.zemris.java.tecaj_14.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model komentara za blog
 * 
 * @author Ivan
 * 
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	private Long id;
	private BlogEntry blogEntry;
	private String usersEMail;
	private String message;
	private Date postedOn;

	/**
	 * Vraća id komentara
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Postavlja id za određeni komentar.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Vraća blog kojem pripada komentar
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Postavlja blog kojem pripada komentar.
	 * 
	 * @param blogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Vraća email korisnika koji je napisao komentar.
	 * 
	 * @return email
	 */
	@Column(nullable = false, length = 200)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Postavlja email korisnika.
	 * 
	 * @param usersEMail
	 *            email korisnika.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Vraća poruku komentara.
	 * 
	 * @return poruka.
	 */
	@Column(nullable = false, length = 4096)
	public String getMessage() {
		return message;
	}

	/**
	 * Postavlja poruku komentara.
	 * 
	 * @param message
	 *            poruka.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Vraća vrijeme pisanja.
	 * 
	 * @return vrijeme pisanja.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Postavlja vrijeme pisanja.
	 * 
	 * @param postedOn
	 *            vrijeme pisanja.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}