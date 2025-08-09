package com.codewithsam.taskmanager.service;

import com.codewithsam.taskmanager.model.Task;
import com.codewithsam.taskmanager.exception.TaskException;
import com.codewithsam.taskmanager.repository.TaskRepository;
import com.codewithsam.taskmanager.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    public Page<Task> getAllTasks(Pageable pageable) {
        log.debug("Fetching all tasks with pagination: {}", pageable);
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) {
        log.debug("Fetching task with id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> TaskException.notFound("Task not found with id: " + id));
    }

    @Transactional
    public Task createTask(Task task) {
        log.debug("Creating new task: {}", task.getTitle());
        validateTask(task);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        log.debug("Updating task with id: {}", id);
        Task existingTask = getTaskById(id);
        
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setCategory(updatedTask.getCategory());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());
        existingTask.setEstimatedHours(updatedTask.getEstimatedHours());
        existingTask.setCreatedBy(updatedTask.getCreatedBy());
        
        // Handle completion date automatically
        if (updatedTask.getStatus() == Task.TaskStatus.COMPLETED && existingTask.getCompletionDate() == null) {
            existingTask.setCompletionDate(LocalDateTime.now());
        } else if (updatedTask.getStatus() != Task.TaskStatus.COMPLETED) {
            existingTask.setCompletionDate(null);
        }
        
        validateTask(existingTask);
        return taskRepository.save(existingTask);
    }

    @Transactional
    public Task patchTask(Long id, Map<String, Object> updates) {
        log.debug("Patching task with id: {} with updates: {}", id, updates.keySet());
        Task task = getTaskById(id);
        
        updates.forEach((key, value) -> {
            switch (key) {
                case "title" -> task.setTitle((String) value);
                case "description" -> task.setDescription((String) value);
                case "dueDate" -> task.setDueDate(value != null ? LocalDate.parse((String) value) : null);
                case "status" -> {
                    Task.TaskStatus newStatus = Task.TaskStatus.valueOf((String) value);
                    task.setStatus(newStatus);
                    if (newStatus == Task.TaskStatus.COMPLETED && task.getCompletionDate() == null) {
                        task.setCompletionDate(LocalDateTime.now());
                    } else if (newStatus != Task.TaskStatus.COMPLETED) {
                        task.setCompletionDate(null);
                    }
                }
                case "priority" -> task.setPriority(Task.Priority.valueOf((String) value));
                case "category" -> task.setCategory((String) value);
                case "assignedTo" -> task.setAssignedTo((String) value);
                case "estimatedHours" -> task.setEstimatedHours((Integer) value);
                case "createdBy" -> task.setCreatedBy((String) value);
            }
        });
        
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        log.debug("Deleting task with id: {}", id);
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    @Transactional
    public Task completeTask(Long id) {
        log.debug("Marking task as completed: {}", id);
        Task task = getTaskById(id);
        
        if (task.getStatus() == Task.TaskStatus.COMPLETED) {
            throw TaskException.badRequest("Task is already completed");
        }
        
        task.markAsCompleted();
        return taskRepository.save(task);
    }

    @Transactional
    public Task startTask(Long id) {
        log.debug("Starting task: {}", id);
        Task task = getTaskById(id);
        
        if (task.getStatus() == Task.TaskStatus.COMPLETED) {
            throw TaskException.badRequest("Cannot start a completed task");
        }
        
        task.markAsInProgress();
        return taskRepository.save(task);
    }

    public Page<Task> filterTasks(String title, String description, Task.TaskStatus status,
                                Task.Priority priority, String category, String assignedTo,
                                String createdBy, LocalDateTime createdAfter, LocalDateTime createdBefore,
                                LocalDate dueAfter, LocalDate dueBefore, String searchTerm,
                                Pageable pageable) {
        
        log.debug("Filtering tasks with criteria");
        Specification<Task> spec = Specification.where(null);

        if (title != null) spec = spec.and(TaskSpecification.hasTitle(title));
        if (description != null) spec = spec.and(TaskSpecification.hasDescription(description));
        if (status != null) spec = spec.and(TaskSpecification.hasStatus(status));
        if (priority != null) spec = spec.and(TaskSpecification.hasPriority(priority));
        if (category != null) spec = spec.and(TaskSpecification.hasCategory(category));
        if (assignedTo != null) spec = spec.and(TaskSpecification.isAssignedTo(assignedTo));
        if (createdBy != null) spec = spec.and(TaskSpecification.isCreatedBy(createdBy));
        if (createdAfter != null) spec = spec.and(TaskSpecification.createdAfter(createdAfter));
        if (createdBefore != null) spec = spec.and(TaskSpecification.createdBefore(createdBefore));
        if (dueAfter != null) spec = spec.and(TaskSpecification.dueAfter(dueAfter));
        if (dueBefore != null) spec = spec.and(TaskSpecification.dueBefore(dueBefore));
        if (searchTerm != null) spec = spec.and(TaskSpecification.searchText(searchTerm));

        return taskRepository.findAll(spec, pageable);
    }

    public List<Task> getOverdueTasks() {
        log.debug("Fetching overdue tasks");
        return taskRepository.findOverdueTasks(LocalDate.now());
    }

    public List<Task> getTasksDueToday() {
        log.debug("Fetching tasks due today");
        return taskRepository.findTasksDueToday(LocalDate.now());
    }

    public List<Task> getTasksDueWithin(int days) {
        log.debug("Fetching tasks due within {} days", days);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(days);
        return taskRepository.findTasksDueWithinDays(startDate, endDate);
    }

    public List<Task> getHighPriorityPendingTasks() {
        log.debug("Fetching high priority pending tasks");
        return taskRepository.findHighPriorityPendingTasks();
    }

    public Page<Task> getRecentlyUpdatedTasks(Pageable pageable) {
        log.debug("Fetching recently updated tasks");
        return taskRepository.findRecentlyUpdatedTasks(pageable);
    }

    public List<Task> searchTasks(String query) {
        log.debug("Searching tasks with query: {}", query);
        if (query == null || query.trim().isEmpty()) {
            throw TaskException.badRequest("Search query cannot be empty");
        }
        return taskRepository.searchTasks(query.trim());
    }

    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        log.debug("Fetching tasks by status: {}", status);
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        log.debug("Fetching tasks by priority: {}", priority);
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByCategory(String category) {
        log.debug("Fetching tasks by category: {}", category);
        return taskRepository.findByCategory(category);
    }

    public List<Task> getTasksAssignedTo(String assignedTo) {
        log.debug("Fetching tasks assigned to: {}", assignedTo);
        return taskRepository.findByAssignedTo(assignedTo);
    }

    public List<Task> getTasksCreatedBy(String createdBy) {
        log.debug("Fetching tasks created by: {}", createdBy);
        return taskRepository.findByCreatedBy(createdBy);
    }

    @Transactional
    public int bulkUpdateStatus(List<Long> taskIds, Task.TaskStatus newStatus) {
        log.debug("Bulk updating status for {} tasks to {}", taskIds.size(), newStatus);
        int updatedCount = 0;
        
        for (Long taskId : taskIds) {
            Optional<Task> taskOpt = taskRepository.findById(taskId);
            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                task.setStatus(newStatus);
                if (newStatus == Task.TaskStatus.COMPLETED && task.getCompletionDate() == null) {
                    task.setCompletionDate(LocalDateTime.now());
                } else if (newStatus != Task.TaskStatus.COMPLETED) {
                    task.setCompletionDate(null);
                }
                taskRepository.save(task);
                updatedCount++;
            }
        }
        
        return updatedCount;
    }

    @Transactional
    public int bulkDeleteTasks(List<Long> taskIds) {
        log.debug("Bulk deleting {} tasks", taskIds.size());
        int deletedCount = 0;
        
        for (Long taskId : taskIds) {
            if (taskRepository.existsById(taskId)) {
                taskRepository.deleteById(taskId);
                deletedCount++;
            }
        }
        
        return deletedCount;
    }

    private void validateTask(Task task) {
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
            log.warn("Task due date is in the past: {}", task.getDueDate());
            // Allow but log warning for existing tasks with past due dates
        }
        
        if (task.getEstimatedHours() != null && task.getEstimatedHours() < 0) {
            throw TaskException.badRequest("Estimated hours cannot be negative");
        }
        
        if (task.getTitle() != null && task.getTitle().trim().isEmpty()) {
            throw TaskException.badRequest("Title cannot be empty");
        }
    }
}
