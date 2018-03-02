package es.edu.app.service.user;

import es.edu.app.dto.UserDTO;

public interface UserService {

	UserDTO getUser(String username);

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user);

	UserDTO deleteUser(String username);

}
