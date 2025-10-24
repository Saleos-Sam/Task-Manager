package com.codewithsid.taskmanager.config;

import com.codewithsid.taskmanager.model.Task;
import com.codewithsid.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class DataLoader implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        long existingCount = taskRepository.count();
        log.info("Current task count: {}", existingCount);
        
        if (existingCount == 0) {
            log.info("Loading sample data...");
            loadSampleTasks();
            log.info("Sample data loaded successfully!");
        } else {
            log.info("Sample data already exists, skipping data loading");
        }
    }

    private void loadSampleTasks() {
        List<Task> sampleTasks = Arrays.asList(
            Task.builder()
                .title("Implement User Authentication")
                .description("Add JWT-based authentication system to the API")
                .dueDate(LocalDate.now().plusDays(7))
                .status(Task.TaskStatus.IN_PROGRESS)
                .priority(Task.Priority.HIGH)
                .category("Security")
                .assignedTo("john.doe@example.com")
                .estimatedHours(16)
                .createdBy("manager@example.com")
                .build(),

            Task.builder()
                .title("Write API Documentation")
                .description("Create comprehensive documentation for all API endpoints")
                .dueDate(LocalDate.now().plusDays(3))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.MEDIUM)
                .category("Documentation")
                .assignedTo("jane.smith@example.com")
                .estimatedHours(8)
                .createdBy("manager@example.com")
                .build(),

            Task.builder()
                .title("Fix Database Connection Bug")
                .description("Resolve intermittent database connection timeout issues")
                .dueDate(LocalDate.now().plusDays(1))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.URGENT)
                .category("Bug Fix")
                .assignedTo("bob.johnson@example.com")
                .estimatedHours(4)
                .createdBy("support@example.com")
                .build(),

            Task.builder()
                .title("Setup CI/CD Pipeline")
                .description("Configure automated testing and deployment pipeline")
                .dueDate(LocalDate.now().plusDays(14))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.MEDIUM)
                .category("DevOps")
                .assignedTo("alice.brown@example.com")
                .estimatedHours(20)
                .createdBy("cto@example.com")
                .build(),

            Task.builder()
                .title("Performance Testing")
                .description("Conduct load testing for the new API endpoints")
                .dueDate(LocalDate.now().plusDays(10))
                .status(Task.TaskStatus.ON_HOLD)
                .priority(Task.Priority.LOW)
                .category("Testing")
                .assignedTo("charlie.wilson@example.com")
                .estimatedHours(12)
                .createdBy("qa@example.com")
                .build(),

            Task.builder()
                .title("Database Migration")
                .description("Migrate existing data to new schema format")
                .dueDate(LocalDate.now().plusDays(2))
                .status(Task.TaskStatus.COMPLETED)
                .priority(Task.Priority.HIGH)
                .category("Database")
                .assignedTo("david.clark@example.com")
                .estimatedHours(24)
                .createdBy("architect@example.com")
                .build(),

            Task.builder()
                .title("Update Dependencies")
                .description("Update all project dependencies to latest versions")
                .dueDate(LocalDate.now().plusDays(1))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.MEDIUM)
                .category("Maintenance")
                .assignedTo("john.doe@example.com")
                .estimatedHours(6)
                .createdBy("manager@example.com")
                .build(),

            Task.builder()
                .title("Code Review Process")
                .description("Establish standardized code review process and guidelines")
                .dueDate(LocalDate.now().plusDays(5))
                .status(Task.TaskStatus.IN_PROGRESS)
                .priority(Task.Priority.MEDIUM)
                .category("Process")
                .assignedTo("senior.developer@example.com")
                .estimatedHours(10)
                .createdBy("lead@example.com")
                .build(),

            Task.builder()
                .title("Mobile App Integration")
                .description("Integrate task manager with mobile application")
                .dueDate(LocalDate.now().plusDays(21))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.LOW)
                .category("Integration")
                .assignedTo("mobile.dev@example.com")
                .estimatedHours(40)
                .createdBy("product@example.com")
                .build(),

            Task.builder()
                .title("Security Audit")
                .description("Conduct comprehensive security audit of the application")
                .dueDate(LocalDate.now().plusDays(30))
                .status(Task.TaskStatus.TODO)
                .priority(Task.Priority.HIGH)
                .category("Security")
                .assignedTo("security@example.com")
                .estimatedHours(32)
                .createdBy("ciso@example.com")
                .build()
        );

        taskRepository.saveAll(sampleTasks);
    }
}
