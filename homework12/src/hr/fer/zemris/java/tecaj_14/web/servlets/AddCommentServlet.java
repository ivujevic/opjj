package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet za dodavanje komentara.
 * @author Ivan
 *
 */
@WebServlet("/servleti/addComment")
public class AddCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		long userID = (Long)req.getSession().getAttribute("current.user.id");
		long id = Long.valueOf(req.getParameter("id"));
		
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry entry = null;
		BlogUser user = null;
		
		try {
			entry = em.find(BlogEntry.class, id);
			user = em.find(BlogUser.class, userID);
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + "/WEB-INF/pages/NoSuchUser.jsp");
		}
		
		String text = req.getParameter("text");
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(user.geteMail());
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(text);
		blogComment.setBlogEntry(entry);
		
		em.persist(blogComment);

		entry.getComments().add(blogComment);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick() + "/" + entry.getId());
	}
}
