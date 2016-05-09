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
 * Servlet koji postavlja boju.
 * 
 * @author Ivan
 * 
 */
public class SetColor extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		String color = req.getParameter("pickedBgCol");
		req.getSession().setAttribute("pickedBgCol", color);

		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
	}
}
