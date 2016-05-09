/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan
 * 
 */
public class Squares extends HttpServlet {

	/**
	 * Servlet u kojem se ispisuje potencije na intervalu [a,b]
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		Object oA = req.getParameter("a");
		Object oB = req.getParameter("b");

		Integer a;
		Integer b;
		if (oA == null) {
			a = 0;
		}
		else {
			a = Integer.valueOf((String) oA);
		}

		if (oB == null) {
			b = 20;
		}
		else {
			b = Integer.valueOf((String) oB);
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		if (b > a + 20) {
			b = a + 20;
		}

		List<Integer> results = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			results.add(i * i);
		}

		req.setAttribute("results", results);
		req.getRequestDispatcher("WEB-INF/pages/squares.jsp").forward(req, resp);
	}
}
