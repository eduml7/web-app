package es.edu.app.auth;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.HttpStatus;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.exception.UserNotFoundException;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;
import es.edu.app.utils.ExchangeUtils;

public class ViewAuthenticator extends Authenticator {

	private final static Logger LOGGER = Logger.getLogger(ExchangeUtils.class.getName());

	private static final String UNAUTHORIZED = "src/main/resources/html/error/401.html";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";

	@SuppressWarnings("unchecked")
	@Override
	public Result authenticate(HttpExchange httpExchange) {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		Map<String, String> params = (Map<String, String>) httpExchange
				.getAttribute(WebAppExchangeAttributes.PARAMETERS);
		Result result = null;
		if (params.get(USERNAME) == null || params.get(PASSWORD) == null) {
			ExchangeUtils.sendRedirectionResponse(httpExchange, "http://localhost:9010/login");
		}

		try {

			UserDTO user = userService.getUser(params.get(USERNAME));

			if (user != null && user.getPassword().equals(params.get(PASSWORD))) {
				result = new Authenticator.Success(
						new WebAppPrincipal(user.getUsername(), WebAppFlow.LOGIN.getPath(), user.getRoles()));
			} else {
				ExchangeUtils.sendViewResponse(httpExchange, UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getHttpCode());
			}

			String sessionId = UUID.randomUUID().toString();
			httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils.createCookie(
					new Cookie(WebAppCookies.SESSION, sessionId, OffsetDateTime.now().plus(Duration.ofMinutes(5)))));

			Session.getSession().put(sessionId, user);

		} catch (UserNotFoundException e) {
			LOGGER.log(Level.INFO, String.format("User %s unauthorized", params.get(USERNAME)));
			ExchangeUtils.sendViewResponse(httpExchange, UNAUTHORIZED, HttpStatus.NOT_ALLOWED.getHttpCode());
		}

		return result;
	}

}
