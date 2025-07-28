package com.codewithsam.taskmanager.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.codewithsam.taskmanager.model.Task;
public interface TaskRepository extends JpaRepository<Task, Long>,JpaSpecificationExecutor<Task> {
}