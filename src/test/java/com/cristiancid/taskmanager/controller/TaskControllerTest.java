package com.cristiancid.taskmanager.controller;

import com.cristiancid.taskmanager.dto.CreateTaskRequest;
import com.cristiancid.taskmanager.exception.TaskNotFoundException;
import com.cristiancid.taskmanager.exception.UserNotFoundException;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldReturnNoContentWhenDeletingExistingTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
        verify(taskService).deleteTaskById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingTask() throws Exception {
        doThrow(new TaskNotFoundException("task not found"))
                .when(taskService)
                .deleteTaskById(1L);

        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNotFound());
        verify(taskService).deleteTaskById(1L);
    }

    @Test
    void shouldReturnOkWhenCompletingExistingTask() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task = new Task("default title", true, user);
        when(taskService.completeTask(1L)).thenReturn(task);

        mockMvc.perform(patch("/tasks/{id}/complete", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
        verify(taskService).completeTask(1L);
    }

    @Test
    void shouldReturnNotFoundWhenCompletingNonExistingTask() throws Exception {
        doThrow(new TaskNotFoundException("task not found"))
                .when(taskService)
                .completeTask(1L);

        mockMvc.perform(patch("/tasks/{id}/complete", 1L))
                .andExpect(status().isNotFound());
        verify(taskService).completeTask(1L);
    }

    @Test
    void shouldReturnTaskWhenTaskExists() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task = new Task("Default title", false, user);
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Default title"))
                .andExpect(jsonPath("$.completed").value(false));
        verify(taskService).getTaskById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        doThrow(new TaskNotFoundException("task not found"))
                .when(taskService)
                .getTaskById(1L);

        mockMvc.perform(get("/tasks/{id}", 1L))
                .andExpect(status().isNotFound());
        verify(taskService).getTaskById(1L);
    }

    @Test
    void shouldReturnTasksWhenUserHasTasks() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task1 = new Task("task one", false, user);
        Task task2 = new Task("task two", true, user);
        Page<Task> page = new PageImpl<>(List.of(task1, task2));
        when(taskService.getTasksByUserId(eq(1L), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/users/{userId}/tasks", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("task one"))
                .andExpect(jsonPath("$.content[1].completed").value(true))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
        verify(taskService).getTasksByUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void shouldReturnEmptyPageWhenUserHasNoTasks() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Page<Task> page = new PageImpl<>(Collections.emptyList());
        when(taskService.getTasksByUserId(eq(1L), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/users/{userId}/tasks", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));
        verify(taskService).getTasksByUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        doThrow(new UserNotFoundException("user not found"))
                .when(taskService)
                .getTasksByUserId(eq(1L), any(Pageable.class));

        mockMvc.perform(get("/users/{userId}/tasks", 1L))
                .andExpect(status().isNotFound());
        verify(taskService).getTasksByUserId(eq(1L), any(Pageable.class));
    }

    @Test
    void shouldCreateTaskWhenUserExists() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        CreateTaskRequest request = new CreateTaskRequest("new task");
        Task task = new Task(request.getTitle(), false, user);
        when(taskService.createTask(eq(1L), any(CreateTaskRequest.class))).thenReturn(task);

        mockMvc.perform(post("/users/{userId}/tasks", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("new task"));
        verify(taskService).createTask(eq(1L), any(CreateTaskRequest.class));
    }

    @Test
    void shouldReturnNotFoundWhenCreatingTaskForNonExistingUser() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest("new task");
        when(taskService.createTask(eq(1L), any(CreateTaskRequest.class)))
                .thenThrow(new UserNotFoundException("user not found"));

        mockMvc.perform(post("/users/{userId}/tasks", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.error").value("user not found"));
        verify(taskService).createTask(eq(1L), any(CreateTaskRequest.class));
    }

    @Test
    void shouldReturnPagedTasksUsingPageAndSizeParameters() throws Exception {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task1 = new Task("task one", false, user);
        Page<Task> page = new PageImpl<>(List.of(task1));

        when(taskService.getTasksByUserId(eq(1L),any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/users/{userId}/tasks", 1L)
                .param("page","0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
        verify(taskService).getTasksByUserId(eq(1L), any(Pageable.class));

    }
}
