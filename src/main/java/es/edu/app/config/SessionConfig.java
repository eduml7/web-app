package es.edu.app.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.edu.app.dto.UserDTO;

public class SessionConfig {

	private static Map<String, UserDTO> session = null;

	private SessionConfig() {
		// nothing to do here
	}

	public static Map<String, UserDTO> getSession() {

		if (session == null) {
			session = new ConcurrentHashMap<String, UserDTO>();
		}
		return session;

	}
}
