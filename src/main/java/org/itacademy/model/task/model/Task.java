package org.itacademy.model.task.model;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private Priority priority;
    private Status status;
    private Long eventId;

    public Task(String title, String description, LocalDate deadline, Priority priority) {
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.deadline = deadline;
        this.priority = priority;
        this.status = Status.NOT_COMPLETED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}