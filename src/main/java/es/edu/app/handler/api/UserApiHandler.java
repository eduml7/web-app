package es.edu.app.handler.api;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;
import es.edu.app.dto.UserDTO;
import es.edu.app.filter.AuthorizationService;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.utils.ExchangeUtils;
import es.edu.app.utils.JsonUtils;

public class UserApiHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		if (!AuthorizationService.authorizeApi(httpExchange.getRequestMethod(),
				userService.getUser(httpExchange.getPrincipal().getName()).getRoles())) {
			ExchangeUtils.sendForbiddenResponse(httpExchange);
		} else {
			String response = "Method not allowed";
			UserDTO user = null;
			switch (httpExchange.getRequestMethod()) {
			case HttpMethod.POST:
				user = JsonUtils.jsonToObject(ExchangeUtils.getRequestBody(httpExchange), UserDTO.class);
				response = JsonUtils.objectToJson(userService.createUser(user));
				break;
			case HttpMethod.PUT:
				user = JsonUtils.jsonToObject(ExchangeUtils.getRequestBody(httpExchange), UserDTO.class);
				response = JsonUtils.objectToJson(userService.updateUser(user));
				break;
			case HttpMethod.GET:
				user = userService.getUser(ExchangeUtils.getPathParam(httpExchange));
				response = JsonUtils.objectToJson(user);
				break;
			case HttpMethod.DELETE:
				user = userService.deleteUser(ExchangeUtils.getPathParam(httpExchange));
				response = JsonUtils.objectToJson(user);
				break;
			}

			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();

			os.write(response.getBytes());
			os.close();
		}
	}

}
