package es.edu.app.handler.api;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;
import es.edu.app.filter.AuthorizationService;
import es.edu.app.persistence.repository.UserRepository;
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
			String response = "Method not allowed";
			switch (httpExchange.getRequestMethod()) {
			case HttpMethod.POST:
				response = userService.createUser(null).toString();
				break;
			case HttpMethod.PUT:
				response = userService.updateUser(null).toString();
				break;
			case HttpMethod.GET:
				response = userService.getUser("admin").toString();
				break;
			case HttpMethod.DELETE:
				response = userService.deleteUser("").toString();
				break;
			}

			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();

			os.write(response.getBytes());
			os.close();
		}
	}

}
