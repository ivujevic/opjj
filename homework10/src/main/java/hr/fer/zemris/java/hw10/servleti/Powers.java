/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji sprema potencije u xls datoteku.
 * 
 * @author Ivan
 * 
 */
public class Powers extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=powers.xls");
		try {
			Integer a = Integer.valueOf((String) req.getParameter("a"));
			Integer b = Integer.valueOf((String) req.getParameter("b"));
			Integer n = Integer.valueOf((String) req.getParameter("n"));
			if ((a < -100 || a > 100) || (b < -100 || b > 100) || (n < 1 || n > 5) || (b < a)) {
				errorMessage(req, resp);
			}

			HSSFWorkbook hwb = createDocument(a, b, n);
			hwb.write(resp.getOutputStream());
		} catch (NumberFormatException e) {
			resp.getWriter().println("Invalid parameters");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

	}

	/**
	 * Metoda u kojoj se kreira excell dokument.
	 */
	private HSSFWorkbook createDocument(int a, int b, int n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet(i + ".page");
			HSSFRow rowHead = sheet.createRow(0);
			rowHead.createCell(0).setCellValue("Numbers");
			rowHead.createCell(1).setCellValue("Powers");

			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j - a + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		return hwb;

	}

	/**
	 * Metoda u kojoj se ispisuje poruka o pogrešci
	 * 
	 * @param resp
	 *            HttpServletResponse
	 * @param req
	 *            HttpServletRequest
	 * 
	 */
	private void errorMessage(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
