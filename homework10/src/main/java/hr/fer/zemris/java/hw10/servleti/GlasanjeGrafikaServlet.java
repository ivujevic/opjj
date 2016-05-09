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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet u kojem se stvara dijagram trenutnog stanja glasova.
 * 
 * @author Ivan
 * 
 */
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
		Map<String, String> bands = (Map<String, String>) req.getSession().getAttribute("bands");
		Map<String, Integer> results = (Map<String, Integer>) req.getSession().getAttribute("voteResults");
		DefaultPieDataset result = new DefaultPieDataset();

		for (Entry<String, Integer> r : results.entrySet()) {
			result.setValue(bands.get(r.getKey()), r.getValue());

		}
		return result;
	}
}
