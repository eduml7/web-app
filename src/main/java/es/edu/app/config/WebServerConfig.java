package es.edu.app.config;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.auth.ApiAuthenticator;
import es.edu.app.auth.ViewAuthenticator;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.filter.api.ApiContentNegotiationFilter;
import es.edu.app.filter.api.ApiParameterFilter;
import es.edu.app.filter.view.SessionFilter;
import es.edu.app.filter.view.ViewAuthorizationFilter;
import es.edu.app.filter.view.ViewParameterFilter;
import es.edu.app.handler.WebAppHandlerFactory;
import es.edu.app.handler.WebAppHandlerFactoryImpl;

public class WebServerConfig {

	private final static Logger LOGGER = Logger.getLogger(WebServerConfig.class.getName());

	public static void run() throws Exception {

		WebAppHandlerFactory webAppHandlerFactory = new WebAppHandlerFactoryImpl();
		// TODO: a propiedad
		int port = 9010;

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		LOGGER.log(Level.INFO, String.format("Server started at %s", port));

		HttpContext contextPage = server.createContext(WebAppFlow.LOGIN_SUCCESSFUL.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.LOGIN_SUCCESSFUL));
		contextPage.getFilters().add(new ViewParameterFilter());
		contextPage.setAuthenticator(new ViewAuthenticator());

		HttpContext contextPage1 = server.createContext(WebAppFlow.PAGE_1.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_1));
		contextPage1.getFilters().add(new ViewParameterFilter());
		contextPage1.getFilters().add(new SessionFilter());
		contextPage1.getFilters().add(new ViewAuthorizationFilter());

		HttpContext contextPage2 = server.createContext(WebAppFlow.PAGE_2.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_2));
		contextPage2.getFilters().add(new ViewParameterFilter());
		contextPage2.getFilters().add(new SessionFilter());
		contextPage2.getFilters().add(new ViewAuthorizationFilter());

		HttpContext contextPage3 = server.createContext(WebAppFlow.PAGE_3.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_3));
		contextPage3.getFilters().add(new ViewParameterFilter());
		contextPage3.getFilters().add(new SessionFilter());
		contextPage3.getFilters().add(new ViewAuthorizationFilter());

		server.createContext(WebAppFlow.LOGIN.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGIN)).getFilters()
				.add(new ViewParameterFilter());

		server.createContext(WebAppFlow.LOGOUT.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGOUT));

		HttpContext contextApi = server.createContext(WebAppFlow.USER_API.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.USER_API));
		contextApi.setAuthenticator(new ApiAuthenticator(WebAppFlow.USER_API.name()));
		contextApi.getFilters().add(new ApiContentNegotiationFilter());
		contextApi.getFilters().add(new ApiParameterFilter());

		server.setExecutor(null);
		server.start();
	}
}
