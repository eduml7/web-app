package es.edu.app.service.user;

import es.edu.app.dto.UserDTO;
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
		return new UserDTO(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
	}

}
