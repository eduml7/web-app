package es.edu.app.handler.login;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.utils.ExchangeUtils;

/**
 * Deletes all cookies and redirects to login
 * 
 * @author edu
 *
 */
public class LogoutHandler implements HttpHandler {

	private static final String DELETE_CALLER_COOKIE = "caller=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	private static final String DELETE_SESSION_COOKIE = "session=; expires=Thu, 01 Jan 1970 00:00:00 GMT";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, DELETE_CALLER_COOKIE);
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, DELETE_SESSION_COOKIE);
		ExchangeUtils.sendRedirectionResponse(httpExchange, WebAppFlow.LOGIN.getPath());
	}

}
