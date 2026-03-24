package com.cristiancid.taskmanager.controller;

import com.cristiancid.taskmanager.dto.CreateTaskRequest;
import com.cristiancid.taskmanager.dto.UpdateTaskRequest;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Task>> getTasksByUserId(@PathVariable Long userId,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        Page<Task> userTasks = taskService.getTasksByUserId(userId, pageable);
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable Long id,
                                               @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTaskById(id, request));

    }

    @PatchMapping("/tasks/{id}/complete")
    public  ResponseEntity<Task> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }
}
