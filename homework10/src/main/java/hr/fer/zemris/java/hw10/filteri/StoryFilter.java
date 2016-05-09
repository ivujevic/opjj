/**
 * 
 */
package hr.fer.zemris.java.hw10.filteri;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter koji ne dopušta da se stranicama ispod /stories pristupa u neparnim minutama.
 * 
 * @author Ivan
 * 
 */
@WebFilter("/stories/*")
public class StoryFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (Calendar.getInstance().get(Calendar.MINUTE) % 2 == 0) {
			chain.doFilter(request, response);
		}
		else {
			response.getWriter().println("You can see this page only on even minutes");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
