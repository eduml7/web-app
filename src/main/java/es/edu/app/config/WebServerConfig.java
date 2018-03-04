package es.edu.app.config;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.utils.PropertiesUtils;

public class WebServerConfig {

	private final static Logger LOGGER = Logger.getLogger(WebServerConfig.class.getName());
	private final static String SERVER_PORT = "server.port";

	public static void run() throws Exception {

		WebServerContextFactory webAppHandlerFactory = new WebServerContextFactoryImpl();

		int port = Integer.parseInt(PropertiesUtils.getPropValues(SERVER_PORT));
		
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		LOGGER.log(Level.INFO, String.format("Server started at %s", port));

		webAppHandlerFactory.createContext(server, WebAppFlow.LOGIN);
		webAppHandlerFactory.createContext(server, WebAppFlow.LOGIN_SUCCESSFUL);
		webAppHandlerFactory.createContext(server, WebAppFlow.LOGOUT);
		webAppHandlerFactory.createContext(server, WebAppFlow.USER_API);
		webAppHandlerFactory.createContext(server, WebAppFlow.PAGE_1);
		webAppHandlerFactory.createContext(server, WebAppFlow.PAGE_2);
		webAppHandlerFactory.createContext(server, WebAppFlow.PAGE_3);
	
		server.setExecutor(null);
		server.start();
	}
}
