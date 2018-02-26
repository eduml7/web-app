package es.edu.app.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.edu.app.dto.UserDTO;

public class SessionConfig {

	private static Map<Integer, UserDTO> session = null;

	private SessionConfig() {
		// nothing to do here
	}

	public static Map<Integer, UserDTO> getSession() throws Exception {

		if (session == null) {
			session = new ConcurrentHashMap<Integer, UserDTO>();
		}
		return session;

	}
}
