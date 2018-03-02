package es.edu.app.service.user;

import es.edu.app.dto.UserDTO;
import es.edu.app.exception.UserNotFoundException;
import es.edu.app.persistence.entity.User;
import es.edu.app.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO getUser(String username) {
		User userEntity = userRepository.getUser(username);
		if (userEntity == null) {
			throw new UserNotFoundException(username);
		}
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

	@Override
	public UserDTO createUser(UserDTO user) {
		User userEntity = userRepository.createUser(new User(user.getUsername(), user.getPassword(), user.getRoles()));
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		User userEntity = userRepository.getUser(user.getUsername());
		if (userEntity == null) {
			throw new UserNotFoundException(user.getUsername());
		}
		User userUpdated = userRepository.updateUser(new User(user.getUsername(), user.getPassword(), user.getRoles()));
		return new UserDTO(userUpdated.getUsername(), userUpdated.getPassword(), userUpdated.getRoles());
	}

	@Override
	public UserDTO deleteUser(String username) {
		User userEntity = userRepository.getUser(username);
		if (userEntity == null) {
			throw new UserNotFoundException(username);
		}
		userRepository.deleteUser(username);
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

}
