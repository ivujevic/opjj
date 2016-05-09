package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji služi za prikazivanje sadržaja bloga.
 * @author Ivan
 *
 */
@WebServlet("/servleti/author/*")
public class PrikazBloga extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	HttpServletRequest req;
	HttpServletResponse resp;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] params = req.getPathInfo().split("/");
		
		this.req = req;
		this.resp = resp;
		
		if(params.length == 2) {
			showBlogList(params[1]);
			return;
		} else if(params.length == 3) {
			if(params[2].equals("new")) {
				doNew(params[1]);
				return;
			} else if(params[2].equals("edit")) {
				doEdit(params[1]);
				return;
			} else {
				showBlogEntry(params[2]);
				return;
			}
			
		} else {
			resp.sendRedirect(req.getContextPath() + "/WEB-INF/pages/NoSuchUser.jsp");
			return;
		}
		
	}
	
	/**
	 * If given a save request (via the URI), forwards it to a {@link #doSave()} method.<br>
	 * Otherwise displays an error page.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.req = req;
		this.resp = resp;
		
		String[] params = req.getPathInfo().split("/");
		System.out.println(params[params.length - 1]);

		if(params[params.length - 1].equals("save")) {
			doSave();
			return;
		} else {
			resp.sendRedirect(req.getContextPath() + "/WEB-INF/pages/NoSuchUser.jsp");
		}
			
	}

	/**
	 * Ako zapis postoji mijenja ga inače stvara novi zapis.
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doSave() throws ServletException, IOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		long id = (Long)req.getSession().getAttribute("current.user.id");
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		Date lastModifiedAt = new Date();
		BlogUser user = (BlogUser)em.find(BlogUser.class, id);
		BlogEntry entry = (BlogEntry)req.getSession().getAttribute("blog.entry");
		
		try {
			BlogEntry original = (BlogEntry)em.find(BlogEntry.class, entry.getId());
			original.setTitle(title);
			original.setText(text);
			original.setLastModifiedAt(lastModifiedAt);
		} catch (Exception e) {
		
			entry.setTitle(title);
			entry.setText(text);
			entry.setLastModifiedAt(lastModifiedAt);
		
			em.persist(entry);
			user.addUserEntry(entry);
		
		} finally {


			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick() + "/" + entry.getId());
		}
		
	}

	/**
	 * Stvara novi zapis.
	 * @param nick
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doNew(String nick) throws ServletException, IOException {
		
		System.out.println("Making a new entry");
		
		if(!checkUser(nick)) {
			req.getRequestDispatcher("/WEB-INF/pages/NoSuchUser.jsp")
			.forward(req, resp);
			return;
		}
		
		System.out.println("Nick is valid...");
		long ID = (Long)(req.getSession().getAttribute("current.user.id"));
		BlogUser user = (BlogUser)JPAEMProvider.getEntityManager().find(BlogUser.class, ID);
		BlogEntry entry = new BlogEntry();
		entry.setComments(new ArrayList<BlogComment>());
		entry.setCreatedAt(new Date());
		entry.setCreator(user);
		
		System.out.println("Setting them attributes...");
		
		req.getSession().setAttribute("blog.entry", entry);
		req.getSession().setAttribute("blog.user", user.getNick());
		
		System.out.println("Forwarding...");

		req.getRequestDispatcher("/WEB-INF/pages/BlogEntryForm.jsp").forward(req, resp);
		
	}
	
	/**
	 * Provjerava je li korisnik vlasnik bloga logiran
	 * @param username
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean checkUser(String username) throws ServletException, IOException {
		String nick = (String)req.getSession().getAttribute("current.user.nick");
		if(!username.equals(nick)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Čita sadržaj
	 * @param nick
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doEdit(String nick) throws ServletException, IOException {
		if(!checkUser(nick)) {
			req.getRequestDispatcher("/WEB-INF/pages/NoSuchUser.jsp")
			.forward(req, resp);
			return;
		}
		
		String sID = req.getParameter("id");
		BlogEntry entry = null;
		BlogUser user = null;
		try {
			long id = Long.valueOf(sID);
			entry = (BlogEntry)JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
			id = (Long)(req.getSession().getAttribute("current.user.id"));
			user = (BlogUser)JPAEMProvider.getEntityManager().find(
					BlogUser.class, id);
		} catch(Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/NoSuchUser.jsp")
			.forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute("blog.entry", entry);
		req.getSession().setAttribute("blog.user", user.getNick());
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntryForm.jsp").forward(req, resp);
	}

	/**
	 * lista zapisa bloga.
	 * @param sID
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showBlogEntry(String sID) throws ServletException, IOException {
		Long id = null;
		try {
			id = Long.valueOf(sID);
		} catch(Exception ignorable) {
		}
		if(id!=null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if(blogEntry!=null) {
				req.setAttribute("blogEntry", blogEntry);
			}
		}
		
		String[] url = req.getRequestURI().split("/");
		String owner = url[url.length - 2];
		
		req.setAttribute("viewed.blog.user", owner);
		req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
		
	}

	/**
	 * Lista korisnikovih zapisa.
	 * @param user
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showBlogList(String user) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		
		for(BlogUser bUser : users) {
			if(bUser.getNick().equals(user)) {
				req.setAttribute("user", user);
				req.getRequestDispatcher("/WEB-INF/pages/ListEntries.jsp")
				.forward(req, resp);
				return;
			}
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/NoSuchUser.jsp")
		.forward(req, resp);
		
	}
	
}