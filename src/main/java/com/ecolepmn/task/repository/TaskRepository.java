package com.ecolepmn.task.repository;

import com.ecolepmn.task.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
}
