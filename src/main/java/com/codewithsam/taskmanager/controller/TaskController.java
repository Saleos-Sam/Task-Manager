package com.codewithsam.taskmanager.controller;

import com.codewithsam.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.codewithsam.taskmanager.model.Task;
import com.codewithsam.taskmanager.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    // Get all tasks with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    // Advanced filtering with pagination
    @GetMapping("/filter")
    public ResponseEntity<Page<Task>> filterTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Task.TaskStatus status,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String assignedTo,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> tasks = taskService.filterTasks(title, description, status, priority, 
                category, assignedTo, createdBy, createdAfter, createdBefore, 
                dueAfter, dueBefore, searchTerm, pageable);
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    // Patch update task (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Task task = taskService.patchTask(id, updates);
        return ResponseEntity.ok(task);
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Mark task as completed
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        Task task = taskService.completeTask(id);
        return ResponseEntity.ok(task);
    }

    // Mark task as in progress
    @PatchMapping("/{id}/start")
    public ResponseEntity<Task> startTask(@PathVariable Long id) {
        Task task = taskService.startTask(id);
        return ResponseEntity.ok(task);
    }

    // Get overdue tasks
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> overdueTasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(overdueTasks);
    }

    // Get tasks due today
    @GetMapping("/due-today")
    public ResponseEntity<List<Task>> getTasksDueToday() {
        List<Task> tasksDueToday = taskService.getTasksDueToday();
        return ResponseEntity.ok(tasksDueToday);
    }

    // Get tasks due within specified days
    @GetMapping("/due-within")
    public ResponseEntity<List<Task>> getTasksDueWithin(@RequestParam int days) {
        List<Task> tasks = taskService.getTasksDueWithin(days);
        return ResponseEntity.ok(tasks);
    }

    // Get high priority pending tasks
    @GetMapping("/high-priority")
    public ResponseEntity<List<Task>> getHighPriorityPendingTasks() {
        List<Task> highPriorityTasks = taskService.getHighPriorityPendingTasks();
        return ResponseEntity.ok(highPriorityTasks);
    }

    // Get recently updated tasks
    @GetMapping("/recent")
    public ResponseEntity<Page<Task>> getRecentlyUpdatedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> recentTasks = taskService.getRecentlyUpdatedTasks(pageable);
        return ResponseEntity.ok(recentTasks);
    }

    // Search tasks
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query) {
        List<Task> searchResults = taskService.searchTasks(query);
        return ResponseEntity.ok(searchResults);
    }

    // Get task statistics
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getTaskStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // Status statistics
        List<Object[]> statusCounts = taskRepository.getTaskCountByStatus();
        Map<String, Long> statusStats = new HashMap<>();
        for (Object[] row : statusCounts) {
            statusStats.put(((Task.TaskStatus) row[0]).name(), (Long) row[1]);
        }
        statistics.put("statusCounts", statusStats);
        
        // Priority statistics
        List<Object[]> priorityCounts = taskRepository.getTaskCountByPriority();
        Map<String, Long> priorityStats = new HashMap<>();
        for (Object[] row : priorityCounts) {
            priorityStats.put(((Task.Priority) row[0]).name(), (Long) row[1]);
        }
        statistics.put("priorityCounts", priorityStats);
        
        // Category statistics
        List<Object[]> categoryCounts = taskRepository.getTaskCountByCategory();
        Map<String, Long> categoryStats = new HashMap<>();
        for (Object[] row : categoryCounts) {
            categoryStats.put((String) row[0], (Long) row[1]);
        }
        statistics.put("categoryCounts", categoryStats);
        
        // Assignee statistics
        List<Object[]> assigneeCounts = taskRepository.getTaskCountByAssignee();
        Map<String, Long> assigneeStats = new HashMap<>();
        for (Object[] row : assigneeCounts) {
            assigneeStats.put((String) row[0], (Long) row[1]);
        }
        statistics.put("assigneeCounts", assigneeStats);
        
        // Overall statistics
        long totalTasks = taskRepository.count();
        long overdueTasks = taskRepository.findOverdueTasks(LocalDate.now()).size();
        long tasksDueToday = taskRepository.findTasksDueToday(LocalDate.now()).size();
        
        Map<String, Long> overallStats = new HashMap<>();
        overallStats.put("total", totalTasks);
        overallStats.put("overdue", overdueTasks);
        overallStats.put("dueToday", tasksDueToday);
        statistics.put("overall", overallStats);
        
        return ResponseEntity.ok(statistics);
    }

    // Get tasks by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Task.Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable String category) {
        List<Task> tasks = taskService.getTasksByCategory(category);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks assigned to a user
    @GetMapping("/assigned/{assignedTo}")
    public ResponseEntity<List<Task>> getTasksAssignedTo(@PathVariable String assignedTo) {
        List<Task> tasks = taskService.getTasksAssignedTo(assignedTo);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks created by a user
    @GetMapping("/created-by/{createdBy}")
    public ResponseEntity<List<Task>> getTasksCreatedBy(@PathVariable String createdBy) {
        List<Task> tasks = taskService.getTasksCreatedBy(createdBy);
        return ResponseEntity.ok(tasks);
    }

    // Bulk operations
    @PostMapping("/bulk-update-status")
    public ResponseEntity<String> bulkUpdateStatus(
            @RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Long> taskIds = (List<Long>) request.get("taskIds");
        Task.TaskStatus newStatus = Task.TaskStatus.valueOf((String) request.get("status"));
        
        int updatedCount = taskService.bulkUpdateStatus(taskIds, newStatus);
        return ResponseEntity.ok("Updated " + updatedCount + " tasks");
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<String> bulkDeleteTasks(@RequestBody List<Long> taskIds) {
        int deletedCount = taskService.bulkDeleteTasks(taskIds);
        return ResponseEntity.ok("Deleted " + deletedCount + " tasks");
    }
}
