package com.codewithsam.taskmanager.controller;
import com.codewithsam.taskmanager.specification.TaskSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.codewithsam.taskmanager.model.Task;
import com.codewithsam.taskmanager.repository.TaskRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before
    ) {
        Specification<Task> spec = (root, query, cb) -> cb.conjunction();

        if (title != null) {
            spec = spec.and(TaskSpecification.hasTitle(title));
        }
        if (status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }
        if (after != null) {
            spec = spec.and(TaskSpecification.createdAfter(after));
        }
        if (before != null) {
            spec = spec.and(TaskSpecification.createdBefore(before));
        }

        return taskRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            return ResponseEntity.ok(taskRepository.save(task));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
