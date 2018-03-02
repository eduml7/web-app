package es.edu.app.handler.login;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class LoginSuccessfulHandler implements HttpHandler {
	private static final String LOGIN_VIEW = "src/main/resources/html/login/login_successful.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);
		if (cookies.containsKey("caller")) {
			ExchangeUtils.sendRedirectionResponse(httpExchange, cookies.get("caller"));
		} else {

			ExchangeUtils.sendResponse(httpExchange, LOGIN_VIEW);
		}

	}

}
