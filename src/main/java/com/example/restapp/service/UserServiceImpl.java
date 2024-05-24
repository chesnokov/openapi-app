package com.example.restapp.service;

import com.example.restapp.mapper.UserMapper;
import com.example.restapp.persistence.entity.UserData;
import com.example.restapp.persistence.repository.TaskRepository;
import com.example.restapp.persistence.repository.UserRepository;
import com.example.restapp.service.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final UserMapper userMapper;


	@Override
	public List<UserEntity> getAllUsers() {
		List<UserData> userData = userRepository.findAll();
		return userData.stream()
				.map( userMapper::userDataToUser).toList();
	}

	@Override
	public Optional<UserEntity> getUserById(Long id) {
		Optional<UserData> userData = userRepository.findById(id);
		return userData.map(userMapper::userDataToUser);
	}

	@Override
	public UserEntity addUser(UserEntity user) {
		UserData userData = userMapper.userToUserData(user);
		userData = userRepository.save(userData);
		userData.getTasks().forEach(taskRepository::save);
		userData = userRepository.save(userData);
		return userMapper.userDataToUser(userData);
	}

	@Override
	public Optional<UserEntity> updateUser(Long id, UserEntity user) {
		Optional<UserData> userData = userRepository.findById(id);
		if(userData.isEmpty()) {
			return Optional.empty();
		} else {
			UserData updatedUser = userMapper.userToUserData(user);
			updatedUser.getTasks().forEach(taskRepository::save);
			updatedUser = userRepository.save(updatedUser);
			UserEntity resultUser = userMapper.userDataToUser(updatedUser);
			return Optional.ofNullable(resultUser);
		}
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
