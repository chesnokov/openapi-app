package com.example.restapp.rest.controller;

import com.example.restapp.mapper.UserMapper;
import com.example.restapp.rest.api.UserApi;
import com.example.restapp.rest.model.RestUser;
import com.example.restapp.service.UserService;
import com.example.restapp.service.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserRestController implements UserApi {
	private final UserService userService;
	private final UserMapper userMapper;

	@Override
	public ResponseEntity<List<RestUser>> getAllUsers() {
		List<UserEntity> users = userService.getAllUsers();
		List<RestUser> restUsers = users.stream()
				.map( userMapper::userToRestUser).toList();
		return new ResponseEntity<>(restUsers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RestUser> getUserById(Long userId) {
		Optional<UserEntity> user = userService.getUserById(userId);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			RestUser restUser = userMapper.userToRestUser(user.get());
			return new ResponseEntity<>(restUser, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<RestUser> addUser(RestUser restUser) {
		UserEntity user = userMapper.restUserToUser(restUser);
		user = userService.addUser(user);
		RestUser resultUser = userMapper.userToRestUser(user);
		return new ResponseEntity<>(resultUser, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RestUser> updateUser(Long userId, RestUser restUser) {
		UserEntity user = userMapper.restUserToUser(restUser);
		Optional<UserEntity> updatedUser = userService.updateUser(userId, user);
		if(updatedUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			RestUser resultUser = userMapper.userToRestUser(updatedUser.get());
			return new ResponseEntity<>(resultUser, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> deleteUser(Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
