package org.itacademy.domain.task.dto;

import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;

import java.time.LocalDate;

public class TaskDtoRequest {
    private String title;
    private String description;
    private LocalDate deadline;
    private Priority priority;
    private Status status;
    private Long eventId;

    public TaskDtoRequest(String title, String description, LocalDate deadline, Priority priority, Status status, Long eventId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.eventId = eventId;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Long getEventId() {
        return eventId;
    }

    public Status getStatus() {
        return status;
    }
}
