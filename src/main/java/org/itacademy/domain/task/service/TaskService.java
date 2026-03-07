package org.itacademy.domain.task.service;


import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;

import java.util.List;

public record TaskService(TaskRepository taskRepository) {

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }
}
