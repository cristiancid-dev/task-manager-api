package com.cristiancid.taskmanager.repository;

import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void shouldReturnTasksWhenUserHasTasks() {
        User user = new User("Cristian", "ccidbe@gmail.com");
        Task task1 = new Task("first task", false, user);
        Task task2 = new Task("second task", false, user);
        testEntityManager.persist(user);
        testEntityManager.persist(task1);
        testEntityManager.persist(task2);
        testEntityManager.flush();

        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoTasks() {
        User user = new User("Cristian", "ccidbe@gmail.com");
        testEntityManager.persist(user);
        testEntityManager.flush();

        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertTrue(tasks.isEmpty()) ;
    }

    @Test
    void shouldReturnEmptyListWhenUserDoesNotExist() {
        List<Task> tasks = taskRepository.findByUserId(999L);

        assertTrue(tasks.isEmpty());
    }
}
