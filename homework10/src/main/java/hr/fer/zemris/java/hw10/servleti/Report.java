/**
 * 
 */
package hr.fer.zemris.java.hw10.servleti;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * @author Ivan
 * 
 */
public class Report extends HttpServlet {

	/**
	 * Servlet koji prikazuje grafički trenutnu upotrebu Operacijskih sustava.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "OS usage");
		req.setAttribute("reportImage", chart);
		File file = new File("c:/slika.png");
		ChartUtilities.saveChartAsJPEG(file, chart, 500, 500);
		req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
	}

	/**
	 * @param dataset
	 * @param string
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * @return
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;
	}
}
