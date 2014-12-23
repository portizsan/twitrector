package es.portizsan.twitrector.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ObjectifyLoader implements ServletContextListener {

	static {

		// ObjectifyService.register(UserAccess.class);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
	}

}
