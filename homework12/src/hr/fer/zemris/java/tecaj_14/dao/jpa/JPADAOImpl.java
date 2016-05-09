package hr.fer.zemris.java.tecaj_14.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_14.dao.DAO;
import hr.fer.zemris.java.tecaj_14.dao.DAOException;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;


public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getBlogUsers() {
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = (List<BlogUser>)JPAEMProvider.getEntityManager().
				createQuery("Select bu from BlogUser as bu").
				getResultList();
		return blogUsers;
	}

}