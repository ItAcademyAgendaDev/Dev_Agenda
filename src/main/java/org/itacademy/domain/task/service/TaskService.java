package org.itacademy.domain.task.service;


import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;

public record TaskService(TaskRepository taskRepository) {

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
}
