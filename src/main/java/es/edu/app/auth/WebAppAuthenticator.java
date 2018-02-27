package es.edu.app.auth;

import java.util.Map;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class WebAppAuthenticator extends Authenticator{

	@SuppressWarnings("unused")
	@Override
	public Result authenticate(HttpExchange httpExchange) {
		//TODO: UID y 5 minutos actualziar la expiration
		httpExchange.getResponseHeaders().add("set-cookie", "world=Mars; expires=Sat, 03 May 2025 17:44:22 GMT");
		Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
		User user = PersistenceEngine.getPersistence().get(params.get("username"));
		return (user != null && user.getPassword().equals(params.get("password")) )?new Authenticator.Success(new HttpPrincipal(user.getUsername(),"PAGES")) : new Authenticator.Failure(401);
	}

}
