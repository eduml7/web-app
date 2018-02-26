package es.edu.app.config;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.auth.WebAppAuthenticator;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.filter.ParameterFilter;
import es.edu.app.handler.factory.WebAppHandlerFactory;
import es.edu.app.handler.factory.WebAppHandlerFactoryImpl;

public class WebServerConfig {

	public static void run() throws Exception {
		WebAppHandlerFactory webAppHandlerFactory = new WebAppHandlerFactoryImpl();
		// TODO: a propiedad
		int port = 9001;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		// TODO: LOGBACK y logger
		System.out.println("server started at " + port);
		HttpContext contextPage = server.createContext(WebAppFlow.PAGE_CONTROLLER.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_CONTROLLER));
		contextPage.getFilters().add(new ParameterFilter());
		contextPage.setAuthenticator(new WebAppAuthenticator());
		server.createContext(WebAppFlow.LOGIN.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGIN));
		server.createContext(WebAppFlow.API.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.API))
				.setAuthenticator(new BasicAuthenticator(WebAppFlow.API.name()) {
					// TODO: verificar credenciales contra la BD
					@Override
					public boolean checkCredentials(String user, String pwd) {
						return user.equals("test") && pwd.equals("test");
					}
				});
		// TODO: ver esto
		server.setExecutor(null);
		server.start();
	}
}
