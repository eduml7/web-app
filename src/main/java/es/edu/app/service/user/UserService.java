package es.edu.app.service.user;

import es.edu.app.dto.UserDTO;

/**
 * Service layer to access user repository
 * 
 * @author edu
 *
 */
public interface UserService {

	UserDTO getUser(String username);

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user);

	UserDTO deleteUser(String username);

}
