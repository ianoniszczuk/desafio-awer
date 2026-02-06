package com.awer.backend.services;
import com.awer.backend.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Page<Task> getAllTasksPaginated(Pageable pageable);
}
