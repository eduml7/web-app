package es.edu.app;

import es.edu.app.config.PersistenceConfig;
import es.edu.app.config.WebServerConfig;

public class ApplicationServer {

	public static void main(String[] args) throws Exception {
		PersistenceConfig.init();
		WebServerConfig.run();
	}
}
