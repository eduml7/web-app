package es.edu.app.auth;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;
import es.edu.app.utils.ExchangeUtils;

public class ViewAuthenticator extends Authenticator {

	@SuppressWarnings("unchecked")
	@Override
	public Result authenticate(HttpExchange httpExchange) {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		Map<String, String> params = (Map<String, String>) httpExchange.getAttribute("parameters");
		Result result = null;
		if (params.get("username") == null) {
			ExchangeUtils.sendRedirectionResponse(httpExchange, "http://localhost:9010/login");
		}

		UserDTO user = userService.getUser(params.get("username"));

		if (user != null && user.getPassword().equals(params.get("password"))) {
			result = new Authenticator.Success(new WebAppPrincipal(user.getUsername(), "VIEW", user.getRoles()));
		} else {
			result = new Authenticator.Failure(401);
		}

		String sessionId = UUID.randomUUID().toString();
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils.createCookie(
				new Cookie(WebAppCookies.SESSION, sessionId, OffsetDateTime.now().plus(Duration.ofMinutes(5)))));

		Session.getSession().put(sessionId, user);

		return result;
	}

}
