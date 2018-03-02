package es.edu.app.exception;

public class WebAppException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public WebAppException(Exception e) {
		super(e);
	}

}
