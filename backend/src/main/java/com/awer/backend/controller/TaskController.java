package com.awer.backend.controller;

import com.awer.backend.model.Task;
import com.awer.backend.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.findAll();

            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        try {
            Task _task = taskRepository.save(new Task(task.getDescription()));
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
