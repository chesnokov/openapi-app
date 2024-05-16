package com.example.restapp.conversion;

import com.example.restapp.model.Task;
import com.example.restapp.repository.TaskData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDataConverter implements Converter<Task, TaskData> {
	@Override
	public TaskData convert(Task source) {
		TaskData taskData = new TaskData();
		taskData.setId(source.getId());
		taskData.setName(source.getName());
		taskData.setDescription(source.getDescription());
		taskData.setCreationDate(source.getCreationDate());
		taskData.setDeadLine(source.getDeadLine());
		return taskData;
	}
}
