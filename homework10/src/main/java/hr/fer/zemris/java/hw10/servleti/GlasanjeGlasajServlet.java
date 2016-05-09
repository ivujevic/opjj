/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet u kojem se prihvaća trenutno glasanje i upisuje u datoteku.
 * 
 * @author Ivan
 * 
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File file = new File(fileName);
		Map<String, Integer> map = new TreeMap<>();
		String id = req.getParameter("id");
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
					StandardCharsets.UTF_8));

			// zastavica koja je postavljena ako je u datoteci pronađen id
			int f = 0;
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.split("\t")[0].equals(id)) {
					f = 1;
					map.put(id, Integer.valueOf(line.split("\t")[1]) + 1);
				}
				else {
					map.put(line.split("\t")[0], Integer.valueOf(line.split("\t")[1]));
				}
			}

			if (f == 0) {
				map.put(id, 1);
			}
			br.close();
		}
		else {
			map.put(id, 1);
		}
		createFile(map, file);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Metoda koja upisuje linije u datoteku.
	 * 
	 * @param map
	 *            Mapa u kojoj su pohranjeni glasovi pojedinog banda pod ključem id
	 * @param file
	 *            datoteka u koju je potrebno upisati linije.
	 */
	private void createFile(Map<String, Integer> map, File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8));
			for (Entry<String, Integer> e : map.entrySet()) {
				bw.write(e.getKey() + "\t" + e.getValue() + "\r\n");
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
