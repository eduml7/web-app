package es.edu.app.auth;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;

public class WebAppAuthenticator extends Authenticator {

	@Override
	public Result authenticate(HttpExchange httpExchange) {
		// TODO: UID y 5 minutos actualziar la expiration
		Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
		Result result = null;
		if (params.get("username") == null) {
			httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			try {
				httpExchange.sendResponseHeaders(301, -1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// TODO: MEter servicio y trabajar con DTO
		User user = PersistenceEngine.getPersistence().get(params.get("username"));

		if (user != null && user.getPassword().equals(params.get("password"))) {
			result = new Authenticator.Success(new HttpPrincipal(user.getUsername(), "PAGES"));
		} else {
			result = new Authenticator.Failure(401);
		}

		String sessionId = UUID.randomUUID().toString();
		httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, CookieUtils.createCookie(
				new Cookie(WebAppCookies.SESSION, sessionId, OffsetDateTime.now().plus(Duration.ofMinutes(5)))));
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setRoles(user.getRoles());
		Session.getSession().put(sessionId, userDTO);

		return result;
	}

}
