package hr.fer.zemris.java.tecaj_14.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred u kojem se implementira jedan zapis bloga.
 * 
 * @author Ivan
 * 
 */
@Entity
@Table(name = "blog_entries")
@NamedQueries({ @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Cacheable(true)
public class BlogEntry {

	private Long id;
	private List<BlogComment> comments = new ArrayList<>();
	private Date createdAt;
	private Date lastModifiedAt;
	private String title;
	private String text;
	private BlogUser creator;

	/**
	 * id zapisa.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * postavlja id zapisa.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Vraća listu komentara na dani zapis.
	 * 
	 * @return lista komentara.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Postavlja listu komentara.
	 * 
	 * @param comments
	 *            lista komentara.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Vraća vrijeme kreiranja zapisa.
	 * 
	 * @return vrijeme kreiranja.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Postavlja vrijeme kreiranja.
	 * 
	 * @param createdAt
	 *            vrijeme kreiranja.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Vrijeme kada je zapis zadnji puta mijenjan.
	 * 
	 * @return vrijeme kada je zadnji puta zapis promijenjen.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Postavlja vrijeme zadnje promejene zapisa.
	 * 
	 * @param lastModified
	 *            vrijeme zadnje promjene
	 */
	public void setLastModifiedAt(Date lastModified) {
		this.lastModifiedAt = lastModified;
	}

	/**
	 * Vraća naslov zapisa.
	 * 
	 * @return naslov
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Postavlja naslov zapisa.
	 * 
	 * @param title
	 *            naslov.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Vraća sadržaj zapisa
	 * 
	 * @return sadržaj zapisa.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Postavlja sadržaj.
	 * 
	 * @param text
	 *            sadržaj zapisa.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Vraća korisnika koji je kreirao zapis.
	 * 
	 * @return korisnik koji je napisao zapis
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Postavlja korisnika koji je stvorio zapis.
	 * 
	 * @param creator
	 *            korisnik koji je stvorio zapis.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}