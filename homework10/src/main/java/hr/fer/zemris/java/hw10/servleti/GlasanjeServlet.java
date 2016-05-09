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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji učitava bendove iz datoteke.
 * 
 * @author Ivan
 * 
 */
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<String, String> bands = new LinkedHashMap<>();
		Map<String, String> songs = new LinkedHashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),
				StandardCharsets.UTF_8));
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			bands.put(line.split("\t")[0], (line.split("\t")[1]));
			songs.put(line.split("\t")[0], line.split("\t")[2]);
		}
		br.close();
		req.getSession().setAttribute("bands", bands);
		req.getSession().setAttribute("songs", songs);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
