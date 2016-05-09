/**
 * 
 */
package hr.fer.zemris.java.webserver.workers;

import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * @author Ivan
 * 
 */
public class EchoParams implements IWebWorker {

	@Override
	public final void processRequest(final RequestContext context) {
		Map<String, String> map = context.getParameters();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n" + "<head>\n" + "	<title> EchoParam </title>\n" + "</head>\n" + "<body> \n"
				+ "<table border=\"1\" >\n");
		for (Entry<String, String> elem : map.entrySet()) {
			builder.append("<tr>\n" + "<td>").append(elem.getKey()).append("</td>\n").append("<td>")
					.append(elem.getValue()).append("</td>\n").append("</tr>\n");
		}
		builder.append("</table>\n");
		builder.append("</body>");
		builder.append("</html>");
		context.setMimeType("text/html");
		context.write(builder.toString());
	}
}
