package es.edu.app.controller.impl;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class LoginSuccessfulControllerImpl implements WebAppController {

	private static final String LOGIN_VIEW = "src/main/resources/html/login/login_successful.html";

	private HttpExchange httpExchange;

	public LoginSuccessfulControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	public void sendResponse() throws IOException {
		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);
		if (cookies.containsKey("caller")) {
			httpExchange.getResponseHeaders().set("Location", cookies.get("caller"));
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(301, -1);
		} else {

			ExchangeUtils.sendResponse(httpExchange, LOGIN_VIEW);
		}
	}
}
