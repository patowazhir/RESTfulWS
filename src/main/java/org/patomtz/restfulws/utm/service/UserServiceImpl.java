package org.patomtz.restfulws.utm.service;

import java.util.List;
import org.patomtz.restfulws.utm.model.User;
import org.patomtz.restfulws.utm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired 
	UserRepository userRepository;
	
	@Override
	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	@Override
	public User getUser(String username) {
		return userRepository.getUser(username);
	}

	@Override
	public User createUser(String username, String password, String fullName) {
		return userRepository.createUser(username, password, fullName);
	}

	@Override
	public boolean login(String username, String password) {
		return userRepository.login(username, password);
	}

	@Override
	public void deleteUser(String username) {
		userRepository.deleteUser(username);
	}

	@Override
	public User updateUser(User user) {
		User updatedUser = userRepository.updateUser(user);
		return updatedUser;
	}
}