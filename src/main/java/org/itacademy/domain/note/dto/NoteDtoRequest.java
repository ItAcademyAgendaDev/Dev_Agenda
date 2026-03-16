package org.itacademy.domain.note.dto;

public class NoteDtoRequest {

    private String description;
    private Long taskId;

    public NoteDtoRequest(String description, Long taskId) {
        this.description = description;
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public Long getTaskId() {
        return taskId;
    }
}