package es.edu.app.auth;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.HttpMethod;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.Role;
import es.edu.app.exception.UserNotFoundException;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;

public class ApiAuthenticator extends Authenticator {
	
	private final static Logger LOGGER = Logger.getLogger(ApiAuthenticator.class.getName());

	protected String realm;

	public ApiAuthenticator(String realm) {
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}

	@Override
	public Result authenticate(HttpExchange t) {
		LOGGER.log(Level.INFO, "Authenticating api user");
		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		Headers rmap = t.getRequestHeaders();
		String auth = rmap.getFirst("Authorization");
		if (auth == null) {
			Headers map = t.getResponseHeaders();
			map.set("WWW-Authenticate", "Basic realm=" + "\"" + realm + "\"");
			return new Authenticator.Retry(401);
		}
		int sp = auth.indexOf(' ');
		if (sp == -1 || !auth.substring(0, sp).equals("Basic")) {
			return new Authenticator.Failure(401);
		}
		byte[] b = Base64.getDecoder().decode(auth.substring(sp + 1));
		String userpass = new String(b);
		int colon = userpass.indexOf(':');
		String uname = userpass.substring(0, colon);
		String pass = userpass.substring(colon + 1);
		UserDTO user;
		try {
			user = userService.getUser(uname);
		} catch (UserNotFoundException e) {
			return new Authenticator.Failure(401);
		}

		if (user != null && user.getPassword().equals(pass)) {
			if (!t.getRequestMethod().equals(HttpMethod.GET) && !user.getRoles().contains(Role.ADMIN)) {
				return new Authenticator.Failure(403);
			} else {
				return new Authenticator.Success(new WebAppPrincipal(uname, realm, user.getRoles()));
			}
		} else {
			Headers map = t.getResponseHeaders();
			map.set("WWW-Authenticate", "Basic realm=" + "\"" + realm + "\"");
			return new Authenticator.Failure(401);
		}
	}
}
