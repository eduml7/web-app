package es.edu.app.enums;

public enum HttpStatus {

	NOT_ALLOWED(405, "Method Not Allowed"), CREATED(201, "Created"), OK(200, "OK"), NO_CONTENT(204,
			"No content"), UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"), NOT_ACCEPTABLE(406,
					"Not Acceptable"), FORBIDDEN(403, "Forbidden"), UNAUTHORIZED(401,
							"Unauthorized"), BAD_REQUEST(400, "Bad request"), NOT_FOUND(404, "Not Found");

	private int httpCode;
	private String description;

	private HttpStatus(int httpCode, String description) {
		this.httpCode = httpCode;
		this.description = description;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public String getDescription() {
		return description;
	}

}
