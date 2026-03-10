package org.itacademy.domain.note.model;

import java.time.LocalDateTime;

public class Note {
    private Long id;
    private String description;
    private LocalDateTime creationDate;
    private Long taskId;

    public Note(String description, Long taskId) {
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.taskId = taskId;
    }

    public Note(Long id, String description, LocalDateTime creationDate, Long taskId) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return String.format(
                """
                NOTE [#%d]
                ---------------------------
                Task ID:     %d
                Created:     %s
                Description: %s
                ---------------------------
                """,
                id,
                taskId,
                creationDate,
                description
        );
    }
}