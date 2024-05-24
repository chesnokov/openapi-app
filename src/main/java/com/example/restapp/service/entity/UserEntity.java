package com.example.restapp.service.entity;

import com.example.restapp.persistence.entity.TaskData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserEntity {
	private Long id;
	private String name;

	private String surname;

	private String mail;

	private List<TaskEntity> tasks=new ArrayList<>();
}