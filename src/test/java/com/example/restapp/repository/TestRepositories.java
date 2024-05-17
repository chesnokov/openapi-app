package com.example.restapp.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackages = {"com.example.restapp.repository"})
public class TestRepositories {
	@Autowired
	UserRepository userRepository;
	@Autowired
	TaskRepository taskRepository;

	@Test
	public void test() {
		UserData user = new UserData();
		user.setName("User1");
		user.setSurname("Surname1");
		user.setMail("user1@example.com");
		user = userRepository.save(user);

		TaskData task1 = new TaskData();
		task1.setName("Task1");
		task1.setDescription("TaskDescription1");
		task1.setCreationDate(LocalDate.of(2024, 5, 14 ));
		task1.setDeadLine(LocalDate.of(2024, 6, 1));
		task1.setUser(user);
		task1 = taskRepository.save(task1);

		TaskData task2 = new TaskData();
		task2.setName("Task2");
		task2.setDescription("TaskDescription2");
		task2.setCreationDate(LocalDate.of(2024, 5, 14 ));
		task2.setDeadLine(LocalDate.of(2024, 6, 1));
		task2.setUser(user);
		task2 = taskRepository.save(task2);

		user.getTasks().add(task1);
		user.getTasks().add(task2);

		userRepository.save(user);

		List<UserData> users = userRepository.findAll();

		Assertions.assertThat(users.get(0).getId()).isEqualTo(1);
		Assertions.assertThat(users.get(0).getTasks().size()).isEqualTo(2);
		Assertions.assertThat(users.get(0)).usingRecursiveComparison().isEqualTo(user);
	}
}
