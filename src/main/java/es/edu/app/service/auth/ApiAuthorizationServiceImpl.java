package es.edu.app.service.auth;

import java.util.List;

import es.edu.app.constants.HttpMethod;
import es.edu.app.enums.Role;

public class ApiAuthorizationServiceImpl implements ApiAuthorizationService {

	public boolean authorize(String httpMethod, List<Role> roles) {
		boolean bool = true;
		if (!httpMethod.equals(HttpMethod.GET) && !roles.contains(Role.ADMIN)) {
			bool = false;
		}
		return bool;
	}

}
