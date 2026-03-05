package org.itacademy.model.task.repository;

import org.itacademy.model.task.model.Task;

public interface TaskRepository {
    Task save(Task task);
}
