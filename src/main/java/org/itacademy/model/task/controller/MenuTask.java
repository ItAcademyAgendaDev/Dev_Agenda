package org.itacademy.model.task.controller;

import org.itacademy.input.InputReader;
import org.itacademy.model.task.controller.builder.TaskBuilder;
import org.itacademy.model.task.model.Task;
import org.itacademy.model.task.service.TaskService;

public class MenuTask {
    private final InputReader scanner;
    private final TaskService taskService;

    public MenuTask(InputReader scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    public void createTask() {


        taskService.createTask(
                new TaskBuilder(scanner)
                        .withTitle()
                        .withDescription()
                        .withDeadline()
                        .withPriority()
                        .build()
        );
    }

    public void printTask(Task task) {
        System.out.println(task.toString());
    }

}
