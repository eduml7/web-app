package es.edu.app.config;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.auth.WebAppAuthenticator;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.filter.AuthorizationFilter;
import es.edu.app.filter.CustomBasicAuth;
import es.edu.app.filter.ParameterFilter;
import es.edu.app.filter.SessionFilter;
import es.edu.app.handler.factory.WebAppHandlerFactory;
import es.edu.app.handler.factory.WebAppHandlerFactoryImpl;

public class WebServerConfig {

	public static void run() throws Exception {
		WebAppHandlerFactory webAppHandlerFactory = new WebAppHandlerFactoryImpl();
		// TODO: a propiedad
		int port = 9010;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		// TODO: LOGBACK y logger
		System.out.println("server started at " + port);

		HttpContext contextPage = server.createContext(WebAppFlow.PAGE_CONTROLLER.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_CONTROLLER));
		contextPage.getFilters().add(new ParameterFilter());
		contextPage.setAuthenticator(new WebAppAuthenticator());
		
		HttpContext contextPage1 = server.createContext(WebAppFlow.PAGE_1.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_1));
		contextPage1.getFilters().add(new ParameterFilter());
		contextPage1.getFilters().add(new SessionFilter());
		contextPage1.getFilters().add(new AuthorizationFilter());
//		
//		HttpContext contextPage2 = server.createContext(WebAppFlow.PAGE_2.getPath(),
//				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_2));
//		contextPage1.getFilters().add(new ParameterFilter());
//		contextPage1.getFilters().add(new SessionFilter());
//	
//		
//		HttpContext contextPage3 = server.createContext(WebAppFlow.PAGE_3.getPath(),
//				webAppHandlerFactory.getHandler(WebAppFlow.PAGE_3));
//		contextPage1.getFilters().add(new ParameterFilter());
//		contextPage1.getFilters().add(new SessionFilter());
//	
	

		server.createContext(WebAppFlow.LOGIN.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGIN)).getFilters().add(new ParameterFilter());;
		
		server.createContext(WebAppFlow.LOGOUT.getPath(), webAppHandlerFactory.getHandler(WebAppFlow.LOGOUT));

		HttpContext contextApi = server.createContext(WebAppFlow.USER_API.getPath(),
				webAppHandlerFactory.getHandler(WebAppFlow.USER_API));
		contextApi.setAuthenticator(new CustomBasicAuth(WebAppFlow.USER_API.name()));
		
		server.setExecutor(null);
		server.start();
	}
}

