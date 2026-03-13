package com.cristiancid.taskmanager.repository;

import com.cristiancid.taskmanager.model.Task;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<@NonNull Task, @NonNull Long> {

}