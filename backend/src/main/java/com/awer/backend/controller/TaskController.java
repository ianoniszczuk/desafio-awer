package com.awer.backend.controller;

import com.awer.backend.model.Task;
import com.awer.backend.repository.TaskRepository;
import com.awer.backend.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        try {
            Task _task = taskService.createTask(new Task(task.getDescription()));
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            Page<Task> taskPage = taskService.getAllTasksPaginated(pageable);

            HttpHeaders headers = buildPaginationHeaders(taskPage, page, size, sortBy, sortDirection);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(taskPage.getContent());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private HttpHeaders buildPaginationHeaders(Page<Task> page, int currentPage, int size, String sortBy, String sortDirection) {
        HttpHeaders headers = new HttpHeaders();

        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replaceQuery("")
                .toUriString();

        StringBuilder linkHeader = new StringBuilder();

        linkHeader.append(String.format("<%s?page=0&size=%d&sortBy=%s&sortDirection=%s>; rel=\"first\"",
                baseUrl, size, sortBy, sortDirection));

        // Previous page
        if (page.hasPrevious()) {
            linkHeader.append(String.format(", <%s?page=%d&size=%d&sortBy=%s&sortDirection=%s>; rel=\"prev\"",
                    baseUrl, currentPage - 1, size, sortBy, sortDirection));
        }

        // Next page
        if (page.hasNext()) {
            linkHeader.append(String.format(", <%s?page=%d&size=%d&sortBy=%s&sortDirection=%s>; rel=\"next\"",
                    baseUrl, currentPage + 1, size, sortBy, sortDirection));
        }

        // Last page
        linkHeader.append(String.format(", <%s?page=%d&size=%d&sortBy=%s&sortDirection=%s>; rel=\"last\"",
                baseUrl, page.getTotalPages() - 1, size, sortBy, sortDirection));

        headers.add("Link", linkHeader.toString());
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        headers.add("X-Current-Page", String.valueOf(currentPage));
        headers.add("X-Page-Size", String.valueOf(size));

        return headers;
    }



}
