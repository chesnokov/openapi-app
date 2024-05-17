package com.example.restapp.conversion;

import com.example.restapp.model.Task;
import com.example.restapp.repository.TaskData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDataToTaskConverter implements Converter<TaskData, Task> {
	@Override
	public Task convert(TaskData source) {
		Task task = new Task();
		task.setId(source.getId());
		task.setName(source.getName());
		task.setDescription(source.getDescription());
		task.setCreationDate(source.getCreationDate());
		task.setDeadLine(source.getDeadLine());
		return task;
	}
}
