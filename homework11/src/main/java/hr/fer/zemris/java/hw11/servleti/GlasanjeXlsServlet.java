/**
 * 
 */
package hr.fer.zemris.java.hw11.servleti;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet koji glasove sprema u xls datoteku i priprema za skidanje.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/glasanje-xls")
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
		int id = Integer.valueOf(req.getSession().getAttribute("ID").toString());
		String dbName = "votingDB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		Connection con = null;
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			System.exit(0);
		}

		cpds.setJdbcUrl(connectionURL);
		cpds.setUser("ivica");
		cpds.setPassword("ivo");
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			con = cpds.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement pst = con
					.prepareStatement("SELECT optionTitle,optionLink,votesCount FROM PollOptions WHERE pollID=? ORDER BY votesCount DESC");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			int i = 1;

			while (rst.next()) {
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(rst.getString(1));
				row.createCell(1).setCellValue(rst.getString(2));
				row.createCell(2).setCellValue(rst.getInt(3));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hwb;
	}

}
