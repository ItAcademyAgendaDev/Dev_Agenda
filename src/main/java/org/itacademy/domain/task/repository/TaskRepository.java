package org.itacademy.domain.task.repository;


import org.itacademy.domain.task.model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    List<Task> findAll();
}
