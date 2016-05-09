package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet za registraciju.
 * @author Ivan
 *
 */
@WebServlet(name = "register", urlPatterns={"/servleti/register"})
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private EntityManager em;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");

		
		UserWrapper user = new UserWrapper(nick, password, firstName, lastName, email);
		doRegister(req, resp, user);
	}

	/**
	 * 
	 * @param req
	 * @param resp
	 * @param user
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doRegister(HttpServletRequest req, HttpServletResponse resp, UserWrapper user) 
			throws ServletException, IOException {
		
		em = JPAEMProvider.getEntityManager();
		
		Map<String, String> errors = user.getErrorList();

		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		
		user.exportToDatabase();
		
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
	
	/**
	 * A wrapper class containing the data contained in a {@link BlogUser}. The class offers<br>
	 * an error.checking method, as well as a method which exports the data contained in the<br>
	 * instance of the class to the database as a new BlogUser.
	 * @author lskukan
	 *
	 */
	private class UserWrapper {
		public String nick;
		public String password;
		public String firstName;
		public String lastName;
		public String email;
		private Map<String, String> errors;
		
		
		
		public UserWrapper(String nick, String password, String firstName,
				String lastName, String email) {
			this.nick = nick;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			errors = new HashMap<String, String>();
		}
		
		
		public Map<String, String> getErrorList() {
			checkEmptyError(nick, "nick");
			checkEmptyError(password, "password");
			checkEmptyError(firstName, "firstName");
			checkEmptyError(lastName, "lastName");
			checkEmptyError(email, "email");
			
			try {
				em.createQuery("SELECT DISTINCT u FROM BlogUser AS u WHERE u.nick = :username")
					.setParameter("username", nick).getSingleResult();
			} catch(Exception e) {
				return errors;
			}
			
			errors.put(nick, "The username is already taken!");
			return errors;
		}
		
		private void checkEmptyError(String val, String key) {
			if(val == null || val.isEmpty()) {
				errors.put(key, "You must not leave this field empty");
			}
		}
		
		
		public void exportToDatabase() {


			BlogUser blogUser = new BlogUser(nick, password, firstName, lastName, email);
			
			em.persist(blogUser);
		}
	}
}
