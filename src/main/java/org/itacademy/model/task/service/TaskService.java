package org.itacademy.model.task.service;


import org.itacademy.model.task.model.Task;
import org.itacademy.model.task.repository.TaskRepository;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
}
