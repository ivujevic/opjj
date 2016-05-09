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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet koji učitava bendove iz datoteke.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		Integer id = Integer.valueOf(req.getParameter("pollID"));
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
					.prepareStatement("SELECT id,optionTitle FROM PollOptions WHERE PollID =? ORDER BY id");
			pst.setInt(1, id);
			Map<Integer, String> pollOptions = new LinkedHashMap<>();
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				pollOptions.put(rst.getInt(1), rst.getString(2));
			}
			pst.close();
			pst = con.prepareStatement("SELECT title,message FROM Polls WHERE id=?");
			pst.setInt(1, id);
			rst = pst.executeQuery();
			String title = "";
			String message = "";
			while (rst.next()) {
				title = rst.getString(1);
				message = rst.getString(2);
			}
			pst.close();
			req.setAttribute("PollOptions", pollOptions);
			req.setAttribute("Title", title);
			req.setAttribute("Message", message);
			req.getSession().setAttribute("ID", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cpds.close();
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
