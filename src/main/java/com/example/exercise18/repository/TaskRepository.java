package com.example.exercise18.repository;

import com.example.exercise18.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void save(Task task);
    void saveBatch(List<Task> tasks);
    void update(Task task);
    Optional<Task> findById(Long id);
    List<Task> findAll();
    void delete(Long id);
}
