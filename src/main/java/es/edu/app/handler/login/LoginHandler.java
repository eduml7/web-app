package es.edu.app.handler.login;

import java.io.IOException;
import java.util.Observable;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.HttpStatus;
import es.edu.app.utils.ExchangeUtils;

/**
 * Handles user login
 * 
 * @author edu
 *
 */
public class LoginHandler extends Observable implements HttpHandler {

	private static final String LOGIN_VIEW = "src/main/resources/html/login/login.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		setChanged();
		notifyObservers(httpExchange);
		ExchangeUtils.sendViewResponse(httpExchange, LOGIN_VIEW, HttpStatus.OK.getHttpCode());
	}

}
