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
 * Servlet za početnu stranicu
 * 
 * @author Ivan
 * 
 */
public class Index extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
