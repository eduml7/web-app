package es.edu.app.handler;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class LogoutHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils
				.createCookie(new Cookie(WebAppCookies.CALLER, "", OffsetDateTime.now().plus(Duration.ofMinutes(-1)))));
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils.createCookie(
				new Cookie(WebAppCookies.SESSION, "", OffsetDateTime.now().plus(Duration.ofMinutes(-1)))));
		ExchangeUtils.sendRedirectionResponse(httpExchange, "http://localhost:9010/login");
	}

}
