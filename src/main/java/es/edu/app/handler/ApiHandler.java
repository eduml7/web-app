package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.ApiController;
import es.edu.app.filter.AuthorizationService;
import es.edu.app.persistence.PersistenceEngine;

public class ApiHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		if (!AuthorizationService.authorizeApi(httpExchange.getRequestMethod(),
				PersistenceEngine.getPersistence().get(httpExchange.getPrincipal().getName()).getRoles())) {
			String response = "Unauthorized";
			httpExchange.sendResponseHeaders(401, response.getBytes().length);
		} else {
			ApiController apiController = new ApiController(httpExchange);
			apiController.sendResponse();
		}
	}

}
