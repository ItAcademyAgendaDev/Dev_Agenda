package org.itacademy.model.task.controller.builder;

import org.itacademy.input.InputReader;
import org.itacademy.model.task.exception.TitleInBlankException;
import org.itacademy.model.task.model.Priority;
import org.itacademy.model.task.model.Status;
import org.itacademy.model.task.model.Task;

import java.time.LocalDate;

public class TaskBuilder {
    private final InputReader scanner;
    private String title;
    private String description;
    private LocalDate deadline;
    private Priority priority;

    public TaskBuilder(InputReader scanner) {
        this.scanner = scanner;
    }

    public TaskBuilder withTitle() {
        try {
            String title = scanner.readString("Please, the title of the task");
            if (title.isBlank()) {
                throw new TitleInBlankException();
            }
            this.title = title;
        } catch (TitleInBlankException e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    public TaskBuilder withDescription() {
        boolean description = scanner.readYesNo("Do you want to describe the task?");
        if (description) {
            this.description = scanner.readString("Please, the description of the task");
        }
        return this;
    }

    public TaskBuilder withDeadline() {
        boolean deadline = scanner.readYesNo("Does the task have dealine?");
        if (deadline) {
            int day = scanner.readInt("enter the day:");
            int month = scanner.readInt("enter the month:");
            int year = scanner.readInt("enter the year:");
            this.deadline = LocalDate.of(year, month, day);
        }
        return this;
    }

    public TaskBuilder withPriority() {
        boolean priorityChose = scanner.readYesNo("Do you want to define the priority?");
        if (priorityChose) {
            int chose = scanner.readInt(
                    """
                            Choose the priority:
                            1. Low
                            2. Middle
                            3. High
                            """
            );
            switch (chose) {
                case 1 -> this.priority = Priority.LOW;
                case 3 -> this.priority = Priority.HIGH;
                default -> this.priority = Priority.MIDDLE;
            }
        } else {
            this.priority = Priority.MIDDLE;
        }
        return this;
    }

    public Task build() {
        return new Task(title, description, deadline, priority);
    }
}