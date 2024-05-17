package com.example.restapp.controller;

import com.example.restapp.api.TaskApi;
import com.example.restapp.model.Task;
import com.example.restapp.repository.TaskData;
import com.example.restapp.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TaskRestController implements TaskApi {

	private final TaskRepository taskRepository;
	private final ConversionService conversionService;
	@Override
	public ResponseEntity<List<Task>> taskGet() {
		List<TaskData> taskData = taskRepository.findAll();
		List<Task> tasks = taskData.stream()
				.map( t -> conversionService.convert(t, Task.class)).toList();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Task> taskTaskIdGet(Long taskId) {
		Optional<TaskData> taskData = taskRepository.findById(taskId);
		if(taskData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Task task = conversionService.convert(taskData.get(), Task.class);
			return new ResponseEntity<>(task, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Task> taskPost(Task task) {
		TaskData taskData = conversionService.convert(task, TaskData.class);
		taskData = taskRepository.save(taskData);
		Task resultTask = conversionService.convert(taskData, Task.class);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Task> taskTaskIdPut(Long taskId, Task task) {
		Optional<TaskData> taskData = taskRepository.findById(taskId);
		if(taskData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			TaskData updatedTask = conversionService.convert(task, TaskData.class);
			updatedTask.setId(taskId);
			updatedTask = taskRepository.save(updatedTask);
			Task resultTask = conversionService.convert(updatedTask, Task.class);
			return new ResponseEntity<>(task, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> taskTaskIdDelete(Long taskId) {
		taskRepository.deleteById(taskId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
