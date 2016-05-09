package hr.fer.zemris.java.tecaj_14.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

public interface DAO {

	
	/**
	 * Dohvaća entry s danim <code>id</code>.
	 * @param id ključ
	 * @return entry ili null ako ne postoji
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Dohvaća listu svih korisnika bloga.
	 * @return lista korisnika
	 */
	public List<BlogUser> getBlogUsers();
	
}