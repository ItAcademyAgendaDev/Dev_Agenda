package org.itacademy.domain.note.dto;

import java.time.LocalDate;

public class NoteDtoResponse {

    private Long id;
    private String description;
    private LocalDate creationDate;
    private Long taskId;

    public NoteDtoResponse(Long id, String description, LocalDate creationDate, Long taskId) {
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

    @Override
    public String toString() {
        return String.format(
                """
                NOTE [#%d]
                ---------------------------
                Description: %s
                Created:     %s
                Task id:     %s
                ---------------------------
                """,
                id,
                description,
                creationDate,
                taskId
        );
    }
}