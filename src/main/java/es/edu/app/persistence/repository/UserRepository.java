package es.edu.app.persistence.repository;

import es.edu.app.persistence.entity.User;

public interface UserRepository {

	User createUser(User user);

	User updateUser(User user);

	User deleteUser(String username);

	User getUser(String username);

}
