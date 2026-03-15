package com.cristiancid.taskmanager.controller;

import com.cristiancid.taskmanager.dto.CreateTaskRequest;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Long userId,
                                           @Valid @RequestBody CreateTaskRequest request) {
        Task newTask = taskService.createTask(userId, request);
        return  new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> userTasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
    }
}
