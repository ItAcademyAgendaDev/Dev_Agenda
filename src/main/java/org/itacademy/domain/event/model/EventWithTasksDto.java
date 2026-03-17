package org.itacademy.domain.event.model;

import org.itacademy.domain.task.dto.TaskDtoResponse;

import java.time.LocalDate;
import java.util.List;

public record EventWithTasksDto(EventDto eventDto,
                                List<TaskDtoResponse> tasks) {

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("""
            
            EVENT DETAILS
            ==========================================
            %s
            ------------------------------------------
            TASKS:
            """, eventDto.toString()));

    if (tasks.isEmpty()) {
        sb.append("There is no tasks linked to this event");
    } else {
        tasks.forEach(t -> sb.append(t.toString()));
    }
    sb.append("\n==========================================");
    return sb.toString();
}
}
