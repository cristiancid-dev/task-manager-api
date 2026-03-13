package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.dto.CreateTaskRequest;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.repository.TaskRepository;
import com.cristiancid.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Long userId, CreateTaskRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Task newTask = new Task(request.getTitle(), false, optionalUser.get());
        return taskRepository.save(newTask);
    }

    public List<Task> getTasksByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        return taskRepository.findByUserId(userId);
    }
}
