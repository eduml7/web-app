package es.edu.app.service.user;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.edu.app.constants.Errors;
import es.edu.app.dto.UserDTO;
import es.edu.app.exception.InvalidUsernameException;
import es.edu.app.exception.UserNotFoundException;
import es.edu.app.persistence.entity.User;
import es.edu.app.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService {
	
	private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO getUser(String username) {
		User userEntity = userRepository.getUser(username);
		if (userEntity == null) {
			LOGGER.log(Level.WARNING, String.format(Errors.USER_NOT_FOUND, username));
			throw new UserNotFoundException(username);
		}
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

	@Override
	public UserDTO createUser(UserDTO user) {
		User userEntity = userRepository.getUser(user.getUsername());
		if (userEntity != null) {
			LOGGER.log(Level.WARNING, String.format(Errors.USERNAME_INVALID, user.getUsername()));
			throw new InvalidUsernameException(user.getUsername());
		}
		User userCreated = userRepository.createUser(new User(user.getUsername(), user.getPassword(), user.getRoles()));
		return new UserDTO(userCreated.getUsername(), userCreated.getPassword(), userCreated.getRoles());
	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		User userEntity = userRepository.getUser(user.getUsername());
		if (userEntity == null) {
			LOGGER.log(Level.WARNING, String.format(Errors.USER_NOT_FOUND, user.getUsername()));
			throw new UserNotFoundException(user.getUsername());
		}
		User userUpdated = userRepository.updateUser(new User(user.getUsername(), user.getPassword(), user.getRoles()));
		return new UserDTO(userUpdated.getUsername(), userUpdated.getPassword(), userUpdated.getRoles());
	}

	@Override
	public UserDTO deleteUser(String username) {
		User userEntity = userRepository.getUser(username);
		if (userEntity == null) {
			LOGGER.log(Level.WARNING, String.format(Errors.USER_NOT_FOUND, username));
			throw new UserNotFoundException(username);
		}
		userRepository.deleteUser(username);
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

}
