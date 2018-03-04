package es.edu.app.exception;

import es.edu.app.constants.Errors;

/**
 * User sent is not allowed to interact with the system
 * 
 * @author edu
 *
 */
public class InvalidUsernameException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public InvalidUsernameException(String username) {
		super(String.format(Errors.USERNAME_INVALID, username));
	}

}
