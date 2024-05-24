package com.example.restapp.mapper;

import com.example.restapp.persistence.entity.TaskData;
import com.example.restapp.rest.model.RestTask;
import com.example.restapp.service.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	TaskEntity restTaskToTask(RestTask restTask);
	TaskEntity taskDataToTask(TaskData taskData);
	RestTask taskToRestTask(TaskEntity task);
	TaskData taskToTaskData(TaskEntity task);
}
