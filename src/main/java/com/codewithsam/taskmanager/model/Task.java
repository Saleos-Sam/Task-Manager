package com.codewithsam.taskmanager.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed = false;
    public Task() {}

    public Task(String title, String description, LocalDate dueDate, boolean completed) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // Getters
    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public LocalDate getDueDate() { return dueDate; }

    public boolean isCompleted() { return completed; }

    // Setters
    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public void setCompleted(boolean completed) { this.completed = completed; }
}