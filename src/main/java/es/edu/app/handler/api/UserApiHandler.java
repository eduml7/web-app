package es.edu.app.handler.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;
import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.HttpStatus;
import es.edu.app.exception.InvalidUsernameException;
import es.edu.app.exception.UserNotFoundException;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.utils.ExchangeUtils;
import es.edu.app.utils.JsonUtils;

/**
 * Handles http request to the users api
 * 
 * @author edu
 *
 */
public class UserApiHandler implements HttpHandler {

	private final static Logger LOGGER = Logger.getLogger(UserApiHandler.class.getName());

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		LOGGER.log(Level.INFO,
				String.format("Handling %s %s request", httpExchange.getRequestMethod(), httpExchange.getRequestURI()));

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		String response = null;
		int httpCode = 0;
		UserDTO user;
		try {
			switch (httpExchange.getRequestMethod()) {
			case HttpMethod.POST:
				user = JsonUtils.jsonToObject(httpExchange.getAttribute(WebAppExchangeAttributes.BODY).toString(),
						UserDTO.class);
				response = JsonUtils.objectToJson(userService.createUser(user));
				httpCode = HttpStatus.CREATED.getHttpCode();
				break;
			case HttpMethod.PUT:
				user = JsonUtils.jsonToObject(httpExchange.getAttribute(WebAppExchangeAttributes.BODY).toString(),
						UserDTO.class);
				response = JsonUtils.objectToJson(userService.updateUser(user));
				httpCode = HttpStatus.OK.getHttpCode();
				break;
			case HttpMethod.GET:
				user = userService.getUser(httpExchange.getAttribute(WebAppExchangeAttributes.PATH).toString());
				response = JsonUtils.objectToJson(user);
				httpCode = HttpStatus.OK.getHttpCode();
				break;
			case HttpMethod.DELETE:
				user = userService.deleteUser(httpExchange.getAttribute(WebAppExchangeAttributes.PATH).toString());
				response = JsonUtils.objectToJson(user);
				httpCode = HttpStatus.OK.getHttpCode();
				break;
			default:
				response = HttpStatus.NOT_ALLOWED.getDescription();
				httpCode = HttpStatus.NOT_ALLOWED.getHttpCode();
			}
		} catch (UserNotFoundException e) {
			response = e.getMessage();
			httpCode = HttpStatus.NOT_FOUND.getHttpCode();
		} catch (InvalidUsernameException e) {
			response = e.getMessage();
			httpCode = HttpStatus.CONFLICT.getHttpCode();
		} finally {
			LOGGER.log(Level.INFO, "Handled request successfully");
			ExchangeUtils.sendApiResponse(httpExchange, response, httpCode);
		}
	}

}
