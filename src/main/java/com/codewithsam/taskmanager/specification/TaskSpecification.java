package com.codewithsam.taskmanager.specification;

import com.codewithsam.taskmanager.model.Task;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                title == null ? cb.conjunction() :
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasDescription(String description) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                description == null ? cb.conjunction() :
                cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Task> hasStatus(Task.TaskStatus status) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                status == null ? cb.conjunction() :
                cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(Task.Priority priority) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                priority == null ? cb.conjunction() :
                cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasCategory(String category) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                category == null ? cb.conjunction() :
                cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }

    public static Specification<Task> isAssignedTo(String assignedTo) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                assignedTo == null ? cb.conjunction() :
                cb.equal(root.get("assignedTo"), assignedTo);
    }

    public static Specification<Task> isCreatedBy(String createdBy) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                createdBy == null ? cb.conjunction() :
                cb.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Task> createdAfter(LocalDateTime date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                date == null ? cb.conjunction() :
                cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }

    public static Specification<Task> createdBefore(LocalDateTime date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                date == null ? cb.conjunction() :
                cb.lessThanOrEqualTo(root.get("createdAt"), date);
    }

    public static Specification<Task> dueBefore(LocalDate date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                date == null ? cb.conjunction() :
                cb.lessThanOrEqualTo(root.get("dueDate"), date);
    }

    public static Specification<Task> dueAfter(LocalDate date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                date == null ? cb.conjunction() :
                cb.greaterThanOrEqualTo(root.get("dueDate"), date);
    }

    public static Specification<Task> isOverdue() {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.and(
                    cb.lessThan(root.get("dueDate"), LocalDate.now()),
                    cb.not(root.get("status").in(Task.TaskStatus.COMPLETED, Task.TaskStatus.CANCELLED))
                );
    }

    public static Specification<Task> hasEstimatedHoursGreaterThan(Integer hours) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                hours == null ? cb.conjunction() :
                cb.greaterThan(root.get("estimatedHours"), hours);
    }

    public static Specification<Task> hasEstimatedHoursLessThan(Integer hours) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                hours == null ? cb.conjunction() :
                cb.lessThan(root.get("estimatedHours"), hours);
    }

    public static Specification<Task> searchText(String searchTerm) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                searchTerm == null ? cb.conjunction() :
                cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + searchTerm.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + searchTerm.toLowerCase() + "%")
                );
    }

    public static Specification<Task> hasNoDueDate() {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.isNull(root.get("dueDate"));
    }

    public static Specification<Task> isHighPriority() {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                root.get("priority").in(Task.Priority.HIGH, Task.Priority.URGENT);
    }

    public static Specification<Task> isPending() {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                root.get("status").in(Task.TaskStatus.TODO, Task.TaskStatus.IN_PROGRESS);
    }
}

