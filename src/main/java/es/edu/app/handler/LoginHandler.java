package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.impl.LoginControllerImpl;
import es.edu.app.utils.ExchangeUtils;

public class LoginHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		ExchangeUtils.parseRequest(httpExchange);
		LoginControllerImpl loginController = new LoginControllerImpl(httpExchange);
		loginController.sendResponse();
	}

}
