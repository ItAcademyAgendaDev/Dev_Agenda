package org.itacademy.domain.task.controller;


import org.itacademy.domain.task.controller.builder.TaskBuilder;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.service.TaskService;
import org.itacademy.input.InputReader;

public record MenuTask(InputReader scanner, TaskService taskService) {

    public void createTask() {
        Task createdTask = taskService.createTask(
                new TaskBuilder(scanner)
                        .withTitle()
                        .withDescription()
                        .withDeadline()
                        .withPriority()
                        .build()
        );
        System.out.println("\n✅ Task created successfully!");
        System.out.println(createdTask);
    }
}
