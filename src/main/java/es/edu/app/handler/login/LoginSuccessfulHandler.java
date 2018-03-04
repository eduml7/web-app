package es.edu.app.handler.login;

import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.enums.HttpStatus;
import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class LoginSuccessfulHandler extends Observable implements HttpHandler {

	private static final String LOGIN_VIEW = "src/main/resources/html/login/login_successful.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		setChanged();
        notifyObservers(httpExchange);

		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);
		if (cookies.containsKey(WebAppCookies.CALLER)) {
			ExchangeUtils.sendRedirectionResponse(httpExchange, cookies.get(WebAppCookies.CALLER));
		} else {
			ExchangeUtils.sendViewResponse(httpExchange, LOGIN_VIEW, HttpStatus.OK.getHttpCode());
		}

	}

}
