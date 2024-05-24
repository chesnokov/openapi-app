package com.example.restapp.mapper;

import com.example.restapp.persistence.entity.UserData;
import com.example.restapp.rest.model.RestUser;
import com.example.restapp.service.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserData userToUserData(UserEntity user);
	RestUser userToRestUser(UserEntity user);
	UserEntity userDataToUser(UserData userData);
	UserEntity restUserToUser(RestUser restUser);
}
