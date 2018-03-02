package es.edu.app.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public UserNotFoundException(String username) {
		super(String.format("The user with username %s does not exist", username));
	}

}
