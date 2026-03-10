package org.itacademy.domain.task.controller;


import org.itacademy.domain.task.controller.builder.TaskBuilder;
import org.itacademy.domain.task.exception.TaskAlreadyCompletedException;
import org.itacademy.domain.task.exception.TaskNotFoundException;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.service.TaskService;
import org.itacademy.input.InputReader;

import java.util.List;

public record MenuTask(InputReader SCANNER, TaskService TASKSERVICE) {

    public void createTask() {
        Task createdTask = TASKSERVICE.createTask(new TaskBuilder(SCANNER)
                        .withTitle()
                        .withDescription()
                        .withDeadline()
                        .withPriority()
                        .build()
        );
        System.out.println("\n✅ Task created successfully!");
        System.out.println(createdTask);
    }

    public void getTasks() {
        List<Task> allTask = TASKSERVICE.listTasks();
        if (allTask.isEmpty()) {
            System.out.println("There are no tasks in the system!");
        } else {
            System.out.println("PRINTING TASK...");
            allTask.forEach(System.out::println);
        }
    }

    public void markAsCompleted() {

        try {
            Long id = (long) SCANNER.readInt("Enter the task id: ");
            TASKSERVICE.markAsCompleted(id);
            System.out.println("Task marked as completed.");
        } catch (TaskNotFoundException | TaskAlreadyCompletedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
