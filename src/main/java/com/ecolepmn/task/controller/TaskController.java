package com.ecolepmn.task.controller;

import com.ecolepmn.task.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/tasks")
public class TaskController {


    private List<Task> tasks = new ArrayList<>();
    private Long taskIdCounter = 1L;

    public TaskController() {
        loadTasksFromJson();
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


    private final String FILE_PATH = "tasks.json"; // Chemin vers votre fichier JSON

    // Méthode pour sauvegarder les tâches dans un fichier JSON
    private void saveTasksToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception en conséquence
        }
    }

    // Méthode pour charger les tâches à partir du fichier JSON
    private void loadTasksFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class);

        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                tasks = objectMapper.readValue(file, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception en conséquence
        }
    }
}





