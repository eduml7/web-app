package es.edu.app.exception;

import es.edu.app.constants.Errors;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public UserNotFoundException(String username) {
		super(String.format(Errors.USER_NOT_FOUND, username));
	}

}
