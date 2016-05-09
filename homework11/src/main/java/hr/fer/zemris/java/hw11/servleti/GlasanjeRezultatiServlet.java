/**
 * 
 */
package hr.fer.zemris.java.hw11.servleti;

import hr.fer.zemris.java.hw11.pools.PoolOption;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet koji služi za prikazivanje trenutnog stanja glasova.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		int id = Integer.valueOf(req.getSession().getAttribute("ID").toString());
		List<PoolOption> results = new ArrayList<>();
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
					.prepareStatement("SELECT id,optionTitle,optionLink,votesCount FROM PollOptions WHERE pollID=? ORDER BY votesCount DESC");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				PoolOption po = new PoolOption(rst.getLong(1), rst.getString(2), rst.getString(3),
						rst.getInt(4));
				results.add(po);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		req.getSession().setAttribute("voteResults", results);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
