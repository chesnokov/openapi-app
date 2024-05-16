package com.example.restapp.controller;

import com.example.restapp.api.UserApi;
import com.example.restapp.model.Task;
import com.example.restapp.model.User;
import com.example.restapp.repository.TaskRepository;
import com.example.restapp.repository.UserData;
import com.example.restapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserRestController implements UserApi {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final ConversionService conversionService;

	@Override
	public ResponseEntity<List<User>> userGet() {
		List<UserData> userData = userRepository.findAll();
		List<User> users = userData.stream()
				.map( u -> conversionService.convert( u, User.class)).toList();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> userUserIdGet(Long userId) {
		Optional<UserData> userData = userRepository.findById(userId);
		if(userData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = conversionService.convert(userData.get(), User.class);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<User> userPost(User user) {
		UserData userData = conversionService.convert(user, UserData.class);
		userData = userRepository.save(userData);
		userData.getTasks().forEach(t-> taskRepository.save(t));
		userData = userRepository.save(userData);
		User resultUser = conversionService.convert(userData, User.class);
		return new ResponseEntity<>(resultUser, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> userUserIdPut(Long userId, User user) {
		Optional<UserData> userData = userRepository.findById(userId);
		if(userData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			UserData updatedUser = conversionService.convert(user, UserData.class);
			updatedUser.getTasks().forEach(t-> taskRepository.save(t));
			updatedUser = userRepository.save(updatedUser);
			User resultUser = conversionService.convert(updatedUser, User.class);
			return new ResponseEntity<>(resultUser, HttpStatus.OK);
		}
	}
}
