package es.edu.app.exception;

import es.edu.app.constants.Errors;

public class InvalidUsernameException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public InvalidUsernameException(String username) {
		super(String.format(Errors.USERNAME_INVALID, username));
	}

}
