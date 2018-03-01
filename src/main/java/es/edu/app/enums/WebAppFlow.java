package es.edu.app.enums;

import java.util.HashMap;
import java.util.Map;

public enum WebAppFlow {
	PAGE_1("/page_1", Role.PAGE_1), PAGE_2("/page_2", Role.PAGE_2), PAGE_3("/page_3", Role.PAGE_3), LOGIN("/login",
			Role.ALL), LOGIN_SUCCESSFUL("/login_successful",
					Role.ALL), USER_API("/v1/api/user", Role.ALL), LOGOUT("/logout", Role.ALL);

	private String path;
	private Role role;

	private WebAppFlow(String path, Role role) {
		this.path = path;
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public String getPath() {
		return path;
	}

	private static Map<String, WebAppFlow> map = new HashMap<String, WebAppFlow>();

	static {
		for (WebAppFlow webAppFlow : WebAppFlow.values()) {
			map.put(webAppFlow.getPath(), webAppFlow);
		}
	}

	public static WebAppFlow fromPath(String path) {
		return map.get(path);
	}

}
