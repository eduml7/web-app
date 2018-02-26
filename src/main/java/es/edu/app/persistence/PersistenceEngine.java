package es.edu.app.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.edu.app.persistence.entity.User;

public class PersistenceEngine {

	private static Map<String, User> inMemoryPersistence = null;

	private PersistenceEngine() {
		// nothing to do here
	}

	public static Map<String, User> getPersistence() {

		if (inMemoryPersistence == null) {
			inMemoryPersistence = new ConcurrentHashMap<String, User>();
		}
		return inMemoryPersistence;

	}

}
