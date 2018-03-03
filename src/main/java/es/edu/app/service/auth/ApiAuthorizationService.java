package es.edu.app.service.auth;

import java.util.List;

import es.edu.app.enums.Role;

public interface ApiAuthorizationService {

	boolean authorize(String httpMethod, List<Role> roles);
}
