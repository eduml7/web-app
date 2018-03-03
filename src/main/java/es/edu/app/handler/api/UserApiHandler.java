package es.edu.app.handler.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.HttpStatus;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.utils.ExchangeUtils;
import es.edu.app.utils.JsonUtils;

public class UserApiHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		String response;
		int httpCode;
		UserDTO user;

		switch (httpExchange.getRequestMethod()) {
		case HttpMethod.POST:
			user = JsonUtils.jsonToObject(ExchangeUtils.getRequestBody(httpExchange), UserDTO.class);
			response = JsonUtils.objectToJson(userService.createUser(user));
			httpCode = HttpStatus.CREATED.getHttpCode();
			break;
		case HttpMethod.PUT:
			user = JsonUtils.jsonToObject(ExchangeUtils.getRequestBody(httpExchange), UserDTO.class);
			response = JsonUtils.objectToJson(userService.updateUser(user));
			httpCode = HttpStatus.OK.getHttpCode();
			break;
		case HttpMethod.GET:
			user = userService.getUser(ExchangeUtils.getPathParam(httpExchange));
			response = JsonUtils.objectToJson(user);
			httpCode = HttpStatus.OK.getHttpCode();
			break;
		case HttpMethod.DELETE:
			user = userService.deleteUser(ExchangeUtils.getPathParam(httpExchange));
			response = JsonUtils.objectToJson(user);
			httpCode = HttpStatus.OK.getHttpCode();
			break;
		default:
			response = HttpStatus.NOT_ALLOWED.getDescription();
			httpCode = HttpStatus.NOT_ALLOWED.getHttpCode();
		}

		ExchangeUtils.sendApiResponse(httpExchange, response, httpCode);

	}

}
