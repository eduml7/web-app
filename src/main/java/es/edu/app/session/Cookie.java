package es.edu.app.session;

import es.edu.app.enums.Role;

public class Cookie {
	public static String getPath(Role roleType) {
		switch (roleType) {
		case PAGE_1:
			return "/seller/home";
		case PAGE_2:
			return "/admin/home";
		case PAGE_3:
			return "/admin/home";
		default:
			return "/login";
		}
	}
}
