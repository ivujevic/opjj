/**
 * 
 */
package hr.fer.zemris.java.hw11.servleti;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet u kojem se prihvaća trenutno glasanje i upisuje u datoteku.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		int PollID = Integer.valueOf(req.getParameter("PollID").toString());

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
					.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + ? WHERE ID =?");
			pst.setInt(1, 1);
			pst.setInt(2, PollID);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
