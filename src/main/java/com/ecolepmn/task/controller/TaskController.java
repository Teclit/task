package com.ecolepmn.task.controller;

import com.ecolepmn.task.entity.Task;
import com.ecolepmn.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(value = {"/", "/index"})
    public String displayStartPage(Model model) {
        model.addAttribute("pageTitle", "Tasks");
        return "taskList";
    }
    @GetMapping("/task/{id}")
    public  Optional<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/task/create")
    public Task saveTaskDetails(@Valid @RequestBody Task task) {
        return taskService.saveTask(task);
    }
    @PutMapping("/task/update/{id}")
    public Task updateTask(@Valid @RequestBody Task updatedTask, @PathVariable Long id) {
        return taskService.updateTask(id, updatedTask);
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/task/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
