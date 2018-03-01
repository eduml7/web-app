package es.edu.app.controller.impl;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.utils.ExchangeUtils;

public class LoginControllerImpl implements WebAppController{

	private static final String LOGIN_VIEW = "src/main/resources/html/login/login.html";

	private HttpExchange httpExchange;

	public LoginControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	public void sendResponse() throws IOException {
		ExchangeUtils.sendResponse(httpExchange, LOGIN_VIEW);
	}

}
