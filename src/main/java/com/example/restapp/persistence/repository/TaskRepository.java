package com.example.restapp.persistence.repository;

import com.example.restapp.persistence.entity.TaskData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskData, Long> {
}
