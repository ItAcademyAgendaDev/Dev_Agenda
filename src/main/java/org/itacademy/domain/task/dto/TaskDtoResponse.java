package org.itacademy.domain.task.dto;

import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;
import org.itacademy.domain.task.model.Task;

import java.time.LocalDate;

public class TaskDtoResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private Priority priority;
    private Status status;
    private Long eventId;

    public TaskDtoResponse(Long id,  String title, String description, LocalDate creationDate, LocalDate deadline, Priority priority, Status status, Long eventId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.eventId = eventId;
    }
}
