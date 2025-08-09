package com.codewithsam.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.codewithsam.taskmanager.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    // Find tasks by status
    List<Task> findByStatus(Task.TaskStatus status);
    
    // Find tasks by priority
    List<Task> findByPriority(Task.Priority priority);
    
    // Find tasks by category
    List<Task> findByCategory(String category);
    
    // Find tasks assigned to a specific person
    List<Task> findByAssignedTo(String assignedTo);
    
    // Find tasks by created by
    List<Task> findByCreatedBy(String createdBy);
    
    // Find overdue tasks
    @Query("SELECT t FROM Task t WHERE t.dueDate < :currentDate AND t.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findOverdueTasks(@Param("currentDate") LocalDate currentDate);
    
    // Find tasks due today
    @Query("SELECT t FROM Task t WHERE t.dueDate = :date AND t.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findTasksDueToday(@Param("date") LocalDate date);
    
    // Find tasks due within specified days
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate AND t.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findTasksDueWithinDays(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Find completed tasks
    List<Task> findByStatusOrderByCompletionDateDesc(Task.TaskStatus status);
    
    // Find tasks by multiple criteria with pagination
    @Query("SELECT t FROM Task t WHERE " +
           "(:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:category IS NULL OR t.category = :category) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority) AND " +
           "(:assignedTo IS NULL OR t.assignedTo = :assignedTo)")
    Page<Task> findTasksWithFilters(@Param("title") String title,
                                   @Param("category") String category,
                                   @Param("status") Task.TaskStatus status,
                                   @Param("priority") Task.Priority priority,
                                   @Param("assignedTo") String assignedTo,
                                   Pageable pageable);
    
    // Find tasks created between dates
    List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find tasks by priority and status
    List<Task> findByPriorityAndStatus(Task.Priority priority, Task.TaskStatus status);
    
    // Get task statistics
    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> getTaskCountByStatus();
    
    @Query("SELECT t.priority, COUNT(t) FROM Task t GROUP BY t.priority")
    List<Object[]> getTaskCountByPriority();
    
    @Query("SELECT t.category, COUNT(t) FROM Task t GROUP BY t.category")
    List<Object[]> getTaskCountByCategory();
    
    // Find high priority pending tasks
    @Query("SELECT t FROM Task t WHERE t.priority IN ('HIGH', 'URGENT') AND t.status IN ('TODO', 'IN_PROGRESS') ORDER BY t.priority DESC, t.createdAt ASC")
    List<Task> findHighPriorityPendingTasks();
    
    // Find recently updated tasks
    @Query("SELECT t FROM Task t ORDER BY t.updatedAt DESC")
    Page<Task> findRecentlyUpdatedTasks(Pageable pageable);
    
    // Search tasks by title or description
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Task> searchTasks(@Param("searchTerm") String searchTerm);
    
    // Count tasks by assignee
    @Query("SELECT t.assignedTo, COUNT(t) FROM Task t WHERE t.assignedTo IS NOT NULL GROUP BY t.assignedTo")
    List<Object[]> getTaskCountByAssignee();
    
    // Find tasks without due date
    List<Task> findByDueDateIsNull();
    
    // Find tasks with estimated hours greater than specified value
    List<Task> findByEstimatedHoursGreaterThan(Integer hours);
}