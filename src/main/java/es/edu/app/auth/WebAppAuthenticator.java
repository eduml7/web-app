package es.edu.app.auth;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class WebAppAuthenticator extends Authenticator{


	@Override
	public Result authenticate(HttpExchange httpExchange) {
		//TODO: UID y 5 minutos actualziar la expiration
		Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");
		
		if (params.get("username") == null) {
			httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate");
			try {
				httpExchange.sendResponseHeaders(301, -1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		User user = PersistenceEngine.getPersistence().get(params.get("username"));
		return (user != null && user.getPassword().equals(params.get("password")) )?new Authenticator.Success(new HttpPrincipal(user.getUsername(),"PAGES")) : new Authenticator.Failure(401);
	}

}
