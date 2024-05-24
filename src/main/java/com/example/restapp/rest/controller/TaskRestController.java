package com.example.restapp.rest.controller;

import com.example.restapp.mapper.TaskMapper;
import com.example.restapp.persistence.repository.TaskRepository;
import com.example.restapp.rest.api.TaskApi;
import com.example.restapp.rest.model.RestTask;
import com.example.restapp.service.TaskService;
import com.example.restapp.service.entity.TaskEntity;
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
	private final TaskService taskService;
	private final TaskMapper taskMapper;
	private final ConversionService conversionService;
	@Override
	public ResponseEntity<List<RestTask>> getAllTasks() {
		List<TaskEntity> tasks = taskService.getAllTasks();
		List<RestTask> restTasks = tasks.stream().map(taskMapper::taskToRestTask).toList();
		return new ResponseEntity<>(restTasks, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RestTask> getTaskById(Long taskId) {
		Optional<TaskEntity> task = taskService.getTaskById(taskId);
		if(task.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			RestTask restTask = taskMapper.taskToRestTask(task.get());
			return new ResponseEntity<>(restTask, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<RestTask> addTask(RestTask restTask) {
		TaskEntity task = taskMapper.restTaskToTask(restTask);
		task = taskService.addTask(task);
		RestTask resultTask = taskMapper.taskToRestTask(task);
		return new ResponseEntity<>(resultTask, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RestTask> updateTask(Long taskId, RestTask restTask) {
		TaskEntity task = taskMapper.restTaskToTask(restTask);
		Optional<TaskEntity> updatedTask = taskService.updateTask(taskId, task);

		if(updatedTask.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			RestTask resultTask = taskMapper.taskToRestTask(updatedTask.get());
			return new ResponseEntity<>(resultTask, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> deleteTask(Long taskId) {
		taskService.deleteTask(taskId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
