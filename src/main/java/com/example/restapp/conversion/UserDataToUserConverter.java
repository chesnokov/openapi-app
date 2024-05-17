package com.example.restapp.conversion;

import com.example.restapp.model.Task;
import com.example.restapp.model.User;
import com.example.restapp.repository.UserData;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserDataToUserConverter implements Converter<UserData, User> {

	private final TaskDataToTaskConverter taskDataToTaskConverter;

	@Override
	public User convert(UserData source) {
		User user = new User();
		user.setId(source.getId());
		user.setName(source.getName());
		user.setSurname(source.getSurname());
		user.setMail(source.getMail());
		List<Task> tasks = source.getTasks().stream()
				.map( t -> taskDataToTaskConverter.convert(t)).toList();
		user.setTasks(tasks);
		return user;
	}
}
