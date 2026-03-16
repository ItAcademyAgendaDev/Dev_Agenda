package org.itacademy.domain.note.model;

import java.time.LocalDate;

public class Note {

    private Long id;
    private String description;
    private LocalDate creationDate;
    private Long taskId;

    public Note(String description, Long taskId) {
        this.description = description;
        this.taskId = taskId;
    }

    public Note(Long id, String description, LocalDate creationDate, Long taskId) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}