package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Početna stranica.
 * @author Ivan
 *
 */
@WebServlet("/servleti/main")
public class MainPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		String nick = req.getParameter("username");
		String password = req.getParameter("password");
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser user = null;
		try {
			user = (BlogUser)em.createQuery("SELECT DISTINCT u FROM BlogUser AS u WHERE u.nick = :username")
				.setParameter("username", nick).getSingleResult();
		} catch(Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/LoginError.jsp").forward(req, resp);
			return;
		}
		
		if(user.checkPasswordForUser(password)) {
			HttpSession session = req.getSession();
			
			session.setAttribute("current.user.id", user.getId());
			session.setAttribute("current.user.fn", user.getFirstName());
			session.setAttribute("current.user.ln", user.getLastName());
			session.setAttribute("current.user.nick", user.getNick());
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/LoginError.jsp").forward(req, resp);
		}
	}
}
