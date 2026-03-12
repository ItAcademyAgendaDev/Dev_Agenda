package org.itacademy.domain.task.controller;


import org.itacademy.domain.task.controller.builder.TaskBuilder;
import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.dto.TaskDtoResponse;
import org.itacademy.domain.exception.TaskAlreadyCompletedException;
import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.task.service.TaskService;
import org.itacademy.input.InputReader;

import java.util.List;

public record MenuTask(InputReader SCANNER, TaskService TASKSERVICE) {

    public void createTask(Long id) {
        TaskDtoRequest request = new TaskBuilder(SCANNER)
                .withTitle()
                .withDescription()
                .withDeadline()
                .withPriority()
                .withEvent(id)
                .build();

        TaskDtoResponse createdTask = TASKSERVICE.createTask(request);

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
        getTasks();
        try {
            Long id = (long) SCANNER.readInt("Enter the task id you want to update: ");
            TASKSERVICE.markAsCompleted(id);
            System.out.println("Task marked as completed.");
        } catch (RequestNotFoundException | TaskAlreadyCompletedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteTask() {
        getTasks();
        try {
            if (!SCANNER.readYesNo("Are you sure you want to delete the task? ")) return;
            Long id = (long) SCANNER.readInt("Enter the task id you want to delete: ");
            TASKSERVICE.deleteTask(id);
            System.out.println("Task deleted successfully!");
        } catch (RequestNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printTasks(String title, List<TaskDtoResponse> tasks, String emptyMessage) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("\n⚠️ " + emptyMessage);
            return;
        }

        System.out.println("\n=== " + title + " ===");
        tasks.forEach(System.out::println);
        System.out.println("====================\n");
    }

}
