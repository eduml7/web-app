package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.WebAppController;
import es.edu.app.controller.impl.LoginSuccessfulControllerImpl;

public class LoginSuccessfulControllerHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

			WebAppController pageController = new LoginSuccessfulControllerImpl(httpExchange);
			pageController.sendResponse();
		

	}

}
