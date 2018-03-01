package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.controller.UserApiController;
import es.edu.app.filter.AuthorizationService;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.utils.ExchangeUtils;

public class UserApiHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		if (!AuthorizationService.authorizeApi(httpExchange.getRequestMethod(),
				userService.getUser(httpExchange.getPrincipal().getName()).getRoles())) {
			ExchangeUtils.sendForbiddenResponse(httpExchange);
		} else {
			UserApiController apiController = new UserApiController(httpExchange);
			apiController.sendResponse();
		}
	}

}
