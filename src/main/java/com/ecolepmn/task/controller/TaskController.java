package com.ecolepmn.task.controller;

import com.ecolepmn.task.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final List<Task> tasks = new ArrayList<>();
    private Long taskIdCounter = 1L;

    public TaskController() {
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setId(taskIdCounter++);
            task.setTitle("Task " + i);
            task.setDescription("Description for Task " + i);
            task.setCompleted(i % 2 != 0);
            tasks.add(task);
        }
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Task createTask(@RequestBody Task newTask) {
        newTask.setId(taskIdCounter++);
        tasks.add(newTask);
        return newTask;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task existingTask = tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
        }
        return existingTask;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}