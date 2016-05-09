/**
 * 
 */
package hr.fer.zemris.java.hw10.listeneri;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener koji prilikom učitavanja aplikacije sprema trenutno vrijeme.
 * 
 * @author Ivan
 * 
 */
@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("loadTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
