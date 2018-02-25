package es.edu.app.config;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.handler.factory.WebAppHandlerFactory;
import es.edu.app.handler.factory.WebAppHandlerFactoryImpl;

public class WebServerConfig {

	public static void run() throws Exception {
		WebAppHandlerFactory webAppHandlerFactory = new WebAppHandlerFactoryImpl();
		// TODO: a propiedad
		int port = 9000;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		// TODO: LOGBACK y logger
		System.out.println("server started at " + port);
		server.createContext(WebAppFlow.PAGE_CONTROLLER.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_CONTROLLER));
		server.createContext(WebAppFlow.LOGIN.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGIN));
		server.createContext(WebAppFlow.API.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.API));
		// TODO: ver esto
		server.setExecutor(null);
		server.start();
	}
}
