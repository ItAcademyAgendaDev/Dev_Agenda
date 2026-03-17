package org.itacademy.domain.task.repository;


import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.model.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);

    List<Task> findAll();

    Optional<Task> findById(Long id);

    void update(Task task);

    List<Task> findByStatus();

    void deleteById(Long id);

    List<Task> findByEventId(Long eventId);
}
