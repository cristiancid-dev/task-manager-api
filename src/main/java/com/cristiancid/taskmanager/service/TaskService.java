package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.dto.CreateTaskRequest;
import com.cristiancid.taskmanager.dto.UpdateTaskRequest;
import com.cristiancid.taskmanager.exception.TaskNotFoundException;
import com.cristiancid.taskmanager.exception.UserNotFoundException;
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
            throw new UserNotFoundException("user not found");
        }
        Task newTask = new Task(request.getTitle(), false, optionalUser.get());
        return taskRepository.save(newTask);
    }

    public List<Task> getTasksByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        return taskRepository.findByUserId(userId);
    }

    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("task not found");
        }
        return optionalTask.get();
    }

    public void deleteTaskById(Long id) {
        if(!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("task not found");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTaskById(Long id, UpdateTaskRequest request) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("task not found");
        }
        Task task = optionalTask.get();
        task.setTitle(request.getTitle());
        return taskRepository.save(task);
    }

    public Task completeTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) {
            throw new TaskNotFoundException("task not found");
        }
        Task task = optionalTask.get();
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
