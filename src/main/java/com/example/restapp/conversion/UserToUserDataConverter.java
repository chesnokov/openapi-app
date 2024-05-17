package com.example.restapp.conversion;

import com.example.restapp.model.User;
import com.example.restapp.repository.TaskData;
import com.example.restapp.repository.UserData;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserToUserDataConverter implements Converter<User, UserData> {

	private final TaskToTaskDataConverter taskToTaskDataConverter;

	@Override
	public UserData convert(User source) {
		UserData userData = new UserData();
		userData.setId(source.getId());
		userData.setName(source.getName());
		userData.setSurname(source.getSurname());
		userData.setMail(source.getMail());
		List<TaskData> tasks = source.getTasks().stream()
				.map(t -> taskToTaskDataConverter.convert(t)).toList();
		tasks.forEach( t-> t.setUser(userData));
		userData.getTasks().addAll(tasks);
		return userData;
	}
}
