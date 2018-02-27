package es.edu.app.enums;

public enum WebAppFlow {
	PAGE_1("/page_1"), LOGIN("/login"), API("/v1/api"), PAGE_CONTROLLER("/page_controller"), USER("/v1/api/user");

	private String path;

	private WebAppFlow(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
