package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.dto.UpdateTaskRequest;
import com.cristiancid.taskmanager.exception.TaskNotFoundException;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    void shouldMarkTaskAsCompletedWhenTaskExists() {
        Long id = 1L;
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task = new Task("task 1", false, user);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.completeTask(id);

        assertTrue(result.isCompleted());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskDoesNotExist() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.completeTask(id);
        });
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).save(any(Task.class));

    }

    @Test
    void shouldUpdateTaskTitleWhenTaskExists() {
        Long id = 1L;
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task = new Task("defaultTitle", false, user);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTitle("updatedTitle");
        Task result = taskService.updateTaskById(id, request);

        assertEquals("updatedTitle", result.getTitle());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenUpdatingNonExistingTask() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        UpdateTaskRequest request = new UpdateTaskRequest();

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTaskById(id, request);
        });
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }

}
