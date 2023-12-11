package com.ecolepmn.task.controller;

import com.ecolepmn.task.entity.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final List<Task> tasks;
    private final String tasksJsonFilePath = "src/main/resources/data/tasks.json";
    private Long taskIdCounter;

    public TaskController() {
        tasks = loadTasksFromJsonFile();
        taskIdCounter = calculateTaskIdCounter();
    }

    private List<Task> loadTasksFromJsonFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(tasksJsonFilePath);
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Long calculateTaskIdCounter() {
        return tasks.stream().mapToLong(Task::getId).max().orElse(0L) + 1;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        Task task = tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (task == null) {
            throw new NotFoundException("Task with ID " + id + " not found");
        }
        return task;
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task newTask) {
        newTask.setId(taskIdCounter++);
        tasks.add(newTask);
        saveTasksToJsonFile(); // Save tasks to the JSON file
        return newTask;
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task existingTask = tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (existingTask == null) {
            throw new NotFoundException("Task with ID " + id + " not found");
        }
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        saveTasksToJsonFile();
        return existingTask;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        Task taskToDelete = tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
        if (taskToDelete != null) {
            tasks.remove(taskToDelete);
            saveTasksToJsonFile();
        } else {
            throw new NotFoundException("Task with ID " + id + " not found");
        }
    }

    private void saveTasksToJsonFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(tasksJsonFilePath), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Custom exception handlers
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
