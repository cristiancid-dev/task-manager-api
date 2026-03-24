package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.dto.UpdateTaskRequest;
import com.cristiancid.taskmanager.exception.TaskNotFoundException;
import com.cristiancid.taskmanager.exception.UserNotFoundException;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.repository.TaskRepository;
import com.cristiancid.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

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

    @Test
    void shouldDeleteTaskWhenTaskExists() {
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> {
            taskService.deleteTaskById(id);
        });
        verify(taskRepository).existsById(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenDeletingNonExistingTask() {
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTaskById(id);
        });
        verify(taskRepository).existsById(id);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldReturnPagedTasksWhenUserExistsAndHasTasks() {
        Pageable pageable = PageRequest.of(0, 2);
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task1 = new Task("First task", false, user);
        Task task2 = new Task("Second task", false, user);
        Page<Task> page = new PageImpl<>(List.of(task1, task2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findByUserId(1L, pageable)).thenReturn(page);

        Page<Task> result = taskService.getTasksByUserId(1L, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        verify(userRepository).findById(1L);
        verify(taskRepository).findByUserId(1L, pageable);
    }

    @Test
    void shouldReturnEmptyPageWhenUserExistsAndHasNoTasks() {
        Pageable pageable = PageRequest.of(0, 2);
        User user = new User("Cristian", "ccidbe@gmail.com");
        Page<Task> page = new PageImpl<>(Collections.emptyList());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findByUserId(1L, pageable)).thenReturn(page);

        Page<Task> result = taskService.getTasksByUserId(1L, pageable);

        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        verify(userRepository).findById(1L);
        verify(taskRepository).findByUserId(1L, pageable);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            taskService.getTasksByUserId(1L, pageable);
        });
        verify(userRepository).findById(1L);
        verify(taskRepository, never()).findByUserId(anyLong(), any(Pageable.class));
    }

}
