package org.itacademy.domain.task.service;


import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.dto.TaskDtoResponse;
import org.itacademy.domain.exception.TaskAlreadyCompletedException;
import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.task.mapper.TaskMapper;
import org.itacademy.domain.task.model.Status;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;

import java.util.List;

public class TaskService {
    private static TaskService instance;
    private final TaskRepository taskRepository;

    private TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public static TaskService getInstance(TaskRepository taskRepository){
        if (instance == null){
            instance = new TaskService(taskRepository);
        }
        return instance;
    }

    public TaskDtoResponse createTask(TaskDtoRequest taskDtoRequest) {
        Task task = TaskMapper.toEntity(taskDtoRequest);
        return TaskMapper.toDto(taskRepository.save(task));
    }

    public List<TaskDtoResponse> listTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public void markAsCompleted(Long id) {

        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Task not found"));

        if (foundTask.getStatus() == Status.COMPLETED) {
            throw new TaskAlreadyCompletedException("Task already completed");
        }

        foundTask.setStatus(Status.COMPLETED);
        taskRepository.update(foundTask);
    }

    public List<TaskDtoResponse> listCompletedTasks() {
        return taskRepository.findByStatus().stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Task not found"));
        taskRepository.deleteById(id);
    }
}
