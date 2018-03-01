package es.edu.app.handler;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;

public class LogoutHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils
				.createCookie(new Cookie(WebAppCookies.CALLER, "", OffsetDateTime.now().plus(Duration.ofMinutes(-1)))));
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils.createCookie(
				new Cookie(WebAppCookies.SESSION, "", OffsetDateTime.now().plus(Duration.ofMinutes(-1)))));

		httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
		httpExchange.getResponseHeaders().set("Pragma", "no-cache");
		httpExchange.getResponseHeaders().set("Expires", "0");
		httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
		httpExchange.sendResponseHeaders(301, -1);
	}

}
