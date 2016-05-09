/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji služi za prikazivanje trenutnog stanja glasova.
 * 
 * @author Ivan
 * 
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Map<String, Integer> results = new TreeMap<>();

		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				StandardCharsets.UTF_8));
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			results.put(line.split("\t")[0], Integer.valueOf(line.split("\t")[1]));
		}
		req.getSession().setAttribute("voteResults", results);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
