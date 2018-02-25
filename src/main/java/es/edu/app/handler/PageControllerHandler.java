package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.WebAppController;
import es.edu.app.controller.impl.PageControllerImpl;
import es.edu.app.utils.ExchangeUtils;

public class PageControllerHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		ExchangeUtils.parseRequest(httpExchange);
		WebAppController pageController = new PageControllerImpl(httpExchange);
		pageController.sendResponse();

	}

}
