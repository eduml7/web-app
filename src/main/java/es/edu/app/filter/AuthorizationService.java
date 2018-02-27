package es.edu.app.filter;

import java.io.IOException;
import java.util.List;

import es.edu.app.constants.HttpMethod;
import es.edu.app.enums.Role;

public class AuthorizationService {

	public static boolean authorizeApi(String httpMethod, List<Role> roles) throws IOException {
		boolean bool = true;
		if (!httpMethod.equals(HttpMethod.GET) && !roles.contains(Role.ADMIN)) {
			bool = false;
		}
		return bool;
	}

}
