package com.ecolepmn.task.service;

import com.ecolepmn.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task saveTask(Task tasks);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);

}
