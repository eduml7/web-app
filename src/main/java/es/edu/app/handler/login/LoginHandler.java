package es.edu.app.handler.login;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.utils.ExchangeUtils;

public class LoginHandler implements HttpHandler {
	private static final String LOGIN_VIEW = "src/main/resources/html/login/login.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		ExchangeUtils.sendResponse(httpExchange, LOGIN_VIEW);
	}

}
