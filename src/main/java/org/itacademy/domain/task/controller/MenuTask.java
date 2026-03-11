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
        printTasks("ALL TASKS", TASKSERVICE.listTasks(), "There are no tasks in the system!");
    }

    public void getCompletedTasks() {
        printTasks("COMPLETED TASKS", TASKSERVICE.listCompletedTasks(), "There are no completed tasks in the system!");
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
    private void printTasks(String title, List<Task> tasks, String emptyMessage) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("\n⚠️ " + emptyMessage);
            return;
        }

        System.out.println("\n=== " + title + " ===");
        tasks.forEach(System.out::println);
        System.out.println("====================\n");
    }

}
