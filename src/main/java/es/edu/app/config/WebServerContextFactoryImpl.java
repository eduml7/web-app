package es.edu.app.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.auth.ApiAuthenticator;
import es.edu.app.auth.ViewAuthenticator;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.filter.api.ApiContentNegotiationFilter;
import es.edu.app.filter.api.ApiParameterFilter;
import es.edu.app.filter.api.validation.RequestValidationFilter;
import es.edu.app.filter.api.validation.UserValidationFilter;
import es.edu.app.filter.view.SessionFilter;
import es.edu.app.filter.view.ViewAuthorizationFilter;
import es.edu.app.filter.view.ViewParameterFilter;
import es.edu.app.handler.api.UserApiHandler;
import es.edu.app.handler.login.LoginHandler;
import es.edu.app.handler.login.LoginSuccessfulHandler;
import es.edu.app.handler.login.LogoutHandler;
import es.edu.app.handler.page.Page1Handler;
import es.edu.app.handler.page.Page2Handler;
import es.edu.app.handler.page.Page3Handler;

public class WebServerContextFactoryImpl implements WebServerContextFactory {

	private final static Logger LOGGER = Logger.getLogger(WebServerContextFactoryImpl.class.getName());

	@Override
	public void createContext(HttpServer server, WebAppFlow flow) {

		LOGGER.log(Level.INFO, String.format("Loading %s HttpContext", flow.getPath()));

		HttpContext context;
		switch (flow) {
		case LOGIN:
			context = server.createContext(WebAppFlow.LOGIN.getPath(), new LoginHandler());
			context.getFilters().add(new ViewParameterFilter());
			break;
		case LOGOUT:
			server.createContext(WebAppFlow.LOGOUT.getPath(), new LogoutHandler());
			break;
		case LOGIN_SUCCESSFUL:
			context = server.createContext(WebAppFlow.LOGIN_SUCCESSFUL.getPath(), new LoginSuccessfulHandler());
			context.getFilters().add(new ViewParameterFilter());
			context.setAuthenticator(new ViewAuthenticator());
			break;
		case USER_API:
			context = server.createContext(WebAppFlow.USER_API.getPath(), new UserApiHandler());
			context.setAuthenticator(new ApiAuthenticator(WebAppFlow.USER_API.name()));
			context.getFilters().add(new RequestValidationFilter());
			context.getFilters().add(new ApiContentNegotiationFilter());
			context.getFilters().add(new ApiParameterFilter());
			context.getFilters().add(new UserValidationFilter());
			break;
		case PAGE_1:
			context = server.createContext(WebAppFlow.PAGE_1.getPath(), new Page1Handler());
			context.getFilters().add(new ViewParameterFilter());
			context.getFilters().add(new SessionFilter());
			context.getFilters().add(new ViewAuthorizationFilter());
			break;
		case PAGE_2:
			context = server.createContext(WebAppFlow.PAGE_2.getPath(), new Page2Handler());
			context.getFilters().add(new ViewParameterFilter());
			context.getFilters().add(new SessionFilter());
			context.getFilters().add(new ViewAuthorizationFilter());
			break;
		case PAGE_3:
			context = server.createContext(WebAppFlow.PAGE_3.getPath(), new Page3Handler());
			context.getFilters().add(new ViewParameterFilter());
			context.getFilters().add(new SessionFilter());
			context.getFilters().add(new ViewAuthorizationFilter());
			break;
		default:
			LOGGER.log(Level.WARNING, String.format("HttpContext %s not supported, dismissed", flow.getPath()));
			break;
		}
	}

}
