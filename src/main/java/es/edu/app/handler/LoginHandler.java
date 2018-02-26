package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.WebAppController;
import es.edu.app.controller.impl.LoginControllerImpl;

public class LoginHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//httpExchange.getRequestHeaders().add("set-cookie", "world=Mars; expires=Sat, 03 May 2025 17:44:22 GMT");
		WebAppController loginController = new LoginControllerImpl(httpExchange);
		loginController.sendResponse();
	}

}
