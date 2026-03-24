package com.cristiancid.taskmanager.repository;

import com.cristiancid.taskmanager.model.Task;
import com.cristiancid.taskmanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    void shouldReturnPagedTasksForGivenUserId() {
        User user1 = new User("Cristian", "ccidbe@gmail.com");
        User user2 = new User("Marta", "martavizcaino7@gmail.com");
        Task task1 = new Task("first task", false, user1);
        Task task2 = new Task("second task", false, user1);
        Task task3 = new Task("third task", false, user1);
        Task task4 = new Task("fourth task", false, user2);
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(task1);
        testEntityManager.persist(task2);
        testEntityManager.persist(task3);
        testEntityManager.persist(task4);
        testEntityManager.flush();

        Pageable pageable = PageRequest.of(0,2);
        Page<Task> tasks = taskRepository.findByUserId(user1.getId(),pageable);

        assertEquals(2, tasks.getContent().size());
        assertEquals(3, tasks.getTotalElements());
        assertTrue(
                tasks.getContent()
                        .stream()
                        .allMatch(task -> task.getUser().getId().equals(user1.getId()))
        );
    }

    @Test
    void shouldReturnEmptyPageWhenUserHasNoTasks() {
        User user = new User("Cristian", "ccidbe@gmail.com");
        testEntityManager.persist(user);
        testEntityManager.flush();

        Pageable pageable = PageRequest.of(0,2);
        Page<Task> tasks = taskRepository.findByUserId(user.getId(), pageable);

        assertTrue(tasks.getContent().isEmpty()) ;
        assertEquals(0, tasks.getTotalElements());
    }

    @Test
    void shouldReturnEmptyPageWhenUserDoesNotExist() {
        Pageable pageable = PageRequest.of(0,2);
        Page<Task> tasks = taskRepository.findByUserId(999L, pageable);

        assertTrue(tasks.getContent().isEmpty()) ;
        assertEquals(0, tasks.getTotalElements());
    }
}
