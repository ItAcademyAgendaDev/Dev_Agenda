package org.itacademy.repository;

import org.itacademy.model.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    void save(Task task);

    Optional<Task> findById(UUID id);

    void markAsCompleted(UUID id);
}
