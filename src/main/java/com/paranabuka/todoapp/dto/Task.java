package com.paranabuka.todoapp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private List<String> comments;

    public Task(String title, String description, String status, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.comments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }
}
