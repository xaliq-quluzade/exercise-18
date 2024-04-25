package com.example.exercise18.controller;

import com.example.exercise18.model.Task;
import com.example.exercise18.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id);
    }

    @PostMapping
    public void saveTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @PostMapping("/multi")
    public void saveTask(@RequestBody List<Task> tasks) {
        taskRepository.saveBatch(tasks);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        taskRepository.update(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.delete(id);
    }
}
