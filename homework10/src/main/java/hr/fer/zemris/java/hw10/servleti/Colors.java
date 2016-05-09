/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za promjenu boje.
 * 
 * @author Ivan
 * 
 */
public class Colors extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		req.getRequestDispatcher("WEB-INF/pages/colors.jsp").forward(req, resp);
	}
}
