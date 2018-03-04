package es.edu.app;

import es.edu.app.config.PersistenceConfig;
import es.edu.app.config.WebServerConfig;

/**
 * Main class
 * 
 * @author edu
 *
 */
public class WebApplication {

	public static void main(String[] args) throws Exception {
		PersistenceConfig.init();
		WebServerConfig.run();
	}
}
