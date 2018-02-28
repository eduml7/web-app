package es.edu.app.handler;

import java.io.IOException;
import java.util.UUID;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.config.SessionConfig;
import es.edu.app.controller.WebAppController;
import es.edu.app.controller.impl.LoginControllerOkImpl;

public class PageControllerHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

			WebAppController pageController = new LoginControllerOkImpl(httpExchange);
			pageController.sendResponse();
		

	}

}
