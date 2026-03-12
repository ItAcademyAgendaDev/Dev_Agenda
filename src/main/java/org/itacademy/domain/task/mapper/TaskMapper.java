package org.itacademy.domain.task.mapper;

import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.dto.TaskDtoResponse;
import org.itacademy.domain.task.model.Task;

public class TaskMapper {
    public static Task toEntity(TaskDtoRequest taskDtoRequest) {
        return new Task(taskDtoRequest.getTitle(), taskDtoRequest.getDescription(), taskDtoRequest.getDeadline(), taskDtoRequest.getPriority(), taskDtoRequest.getEventId());
    }

    public static TaskDtoResponse toDto(Task task) {
        return new TaskDtoResponse(task.getId(), task.getTitle(), task.getDescription(), task.getCreationDate(), task.getDeadline(), task.getPriority(), task.getStatus(), task.getEventId());
    }
}