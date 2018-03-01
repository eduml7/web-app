package es.edu.app.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.edu.app.dto.UserDTO;

public class Session {

	private static Map<String, UserDTO> session = null;

	private Session() {
		// nothing to do here
	}

	public static Map<String, UserDTO> getSession() {

		if (session == null) {
			session = new ConcurrentHashMap<String, UserDTO>();
		}
		return session;

	}
}
