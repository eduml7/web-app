package es.edu.app.exception;

public class SerializationDeserializationException extends RuntimeException {

	private static final long serialVersionUID = -5098638227229794386L;

	public SerializationDeserializationException() {
		super(String.format("There was a problem in de/serialization"));
	}

}
