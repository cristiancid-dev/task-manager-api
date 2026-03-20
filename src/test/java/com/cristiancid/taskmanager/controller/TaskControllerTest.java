package com.cristiancid.taskmanager.controller;

import com.cristiancid.taskmanager.exception.TaskNotFoundException;
import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}
