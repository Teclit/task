package com.ecolepmn.task.service;

import com.ecolepmn.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecolepmn.task.entity.Tasks;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Tasks> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Tasks createTask(Tasks tasks) {
        return taskRepository.save(tasks);
    }

    public Tasks updateTask(Long id, Tasks updatedTask) {
        Tasks existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(existingTask);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
