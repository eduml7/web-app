package es.edu.app.filter;

import com.sun.net.httpserver.BasicAuthenticator;

import es.edu.app.dto.UserDTO;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.service.user.UserService;
import es.edu.app.service.user.UserServiceImpl;

public class CustomBasicAuth extends BasicAuthenticator {

	public CustomBasicAuth(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {

		UserService userService = new UserServiceImpl(new UserRepositoryImpl());

		UserDTO user = userService.getUser(username);
		if (user != null && user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

}
