package com.codewithsam.taskmanager.specification;
import com.codewithsam.taskmanager.model.Task;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasStatus(String status) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(cb.lower(root.get("status")), status.toLowerCase());
    }

    public static Specification<Task> createdAfter(LocalDate date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.greaterThanOrEqualTo(root.<LocalDate>get("createdDate"), date);
    }

    public static Specification<Task> createdBefore(LocalDate date) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.lessThanOrEqualTo(root.<LocalDate>get("createdDate"), date);
    }
}

