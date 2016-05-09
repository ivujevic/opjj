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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet u kojem se stvara dijagram trenutnog stanja glasova.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset);
		req.setAttribute("reportImage", chart);
		resp.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400, true, 0);
	}

	/**
	 * Metoda koja kreira grafikon u obliku torte s rezultatima
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart3D("Results", dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.9f);
		plot.setCircular(true);
		return chart;
	}

	/**
	 * @param req
	 * @return
	 */
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
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
					.prepareStatement("SELECT optionTitle,votesCount FROM PollOptions WHERE pollID=? ORDER BY votesCount DESC");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				result.setValue(rst.getString(1), rst.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
