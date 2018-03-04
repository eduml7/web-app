package es.edu.app.persistence.repository;

import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class UserRepositoryImpl implements UserRepository {

	@Override
	public User getUser(String username) {
		return PersistenceEngine.getPersistence().get(username);
	}

	@Override
	public User createUser(User user) {
		PersistenceEngine.getPersistence().put(user.getUsername(), user);
		return PersistenceEngine.getPersistence().get(user.getUsername());
	}

	@Override
	public User updateUser(User user) {
		return PersistenceEngine.getPersistence().put(user.getUsername(), user);
	}

	@Override
	public User deleteUser(String username) {
		return PersistenceEngine.getPersistence().remove(username);
	}



}
