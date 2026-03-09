package org.itacademy.domain.task.repository;


import org.itacademy.domain.task.model.Task;

public interface TaskRepository {
    Task save(Task task);
}
