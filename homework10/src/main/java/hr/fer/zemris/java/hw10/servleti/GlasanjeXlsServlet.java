/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji glasove sprema u xls datoteku i priprema za skidanje.
 * 
 * @author Ivan
 * 
 */
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=voteResults.xls");
		HSSFWorkbook hwb = creteDocument(req);
		hwb.write(resp.getOutputStream());
	}

	/**
	 * Metoda u kojoj se stvara novi xls dokument
	 * 
	 * @param req
	 * @return
	 */
	private HSSFWorkbook creteDocument(HttpServletRequest req) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Results");
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Id");
		rowHead.createCell(1).setCellValue("Name");
		rowHead.createCell(2).setCellValue("Result");
		Map<String, String> bands = (Map<String, String>) req.getSession().getAttribute("bands");
		Map<String, Integer> results = (Map<String, Integer>) req.getSession().getAttribute("voteResults");

		int i = 1;
		for (Entry<String, Integer> r : results.entrySet()) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(r.getKey());
			row.createCell(1).setCellValue(bands.get(r.getKey()));
			row.createCell(2).setCellValue(r.getValue());
			i++;
		}
		return hwb;
	}

}
