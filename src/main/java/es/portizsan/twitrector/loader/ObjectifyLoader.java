package es.portizsan.twitrector.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

import es.portizsan.twitrector.bean.Twitrector;

public class ObjectifyLoader implements ServletContextListener {

	static {

		ObjectifyService.register(Twitrector.class);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
	}

}
