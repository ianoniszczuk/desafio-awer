package com.awer.backend.services;

import com.awer.backend.model.Task;
import com.awer.backend.repository.TaskRepository;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable; // ← Cambia este import
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAllTasksPaginated(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

}
