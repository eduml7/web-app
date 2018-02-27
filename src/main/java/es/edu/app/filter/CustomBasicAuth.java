package es.edu.app.filter;

import com.sun.net.httpserver.BasicAuthenticator;

import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class CustomBasicAuth extends BasicAuthenticator {

	public CustomBasicAuth(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		User user = PersistenceEngine.getPersistence().get(username);
		if(user != null && user.getPassword().equals(password)) {
			return true;
		}else {
			return false;
		}
	}

}
