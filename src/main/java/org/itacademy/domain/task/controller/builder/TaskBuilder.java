package org.itacademy.domain.task.controller.builder;

import org.itacademy.domain.task.exception.InvalidDateException;
import org.itacademy.domain.task.exception.TitleInBlankException;
import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Task;
import org.itacademy.input.InputReader;

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
        while (true) {
            try {
                String inputTitle = scanner.readString("Please, write the title of the task");

                if (inputTitle == null || inputTitle.isBlank()) {
                    throw new TitleInBlankException("Error: The title cannot be empty or blank.");
                }

                this.title = inputTitle;
                return this;
            } catch (TitleInBlankException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public TaskBuilder withDescription() {
        if (scanner.readYesNo("Do you want to describe the task?")) {
            this.description = scanner.readString("Please, write the description of the task");
        }
        return this;
    }

    public TaskBuilder withDeadline() {
        if (scanner.readYesNo("Does the task have deadline?")) {
            this.deadline = readValidDate();
        }
        return this;
    }

    public TaskBuilder withPriority() {
        boolean priorityChose = scanner.readYesNo("Do you want to define the priority?");
        if (priorityChose) {
            this.priority = setPriority();
        } else {
            this.priority = Priority.MIDDLE;
        }
        return this;
    }

    public Task build() {
        return new Task(title, description, deadline, priority);
    }

    private LocalDate readValidDate() {
        LocalDate inputDate = null;
        while (true) {
            try {
                System.out.println("--- Enter Deadline ---");
                int day = scanner.readInt("Enter day:");
                int month = scanner.readInt("Enter month:");
                int year = scanner.readInt("Enter year:");

                inputDate = LocalDate.of(year, month, day);

                if (inputDate.isBefore(LocalDate.now())) {
                    throw new InvalidDateException("Date cannot be in the past.");
                }
                return inputDate;

            } catch (java.time.DateTimeException e) {
                System.out.println("Invalid date: That day doesn't exist in the calendar. Try again.");
            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Priority setPriority() {

        return switch (scanner.readInt("""
                Choose the priority:
                1. Low
                2. Middle
                3. High
                """)) {
            case 1 -> Priority.LOW;
            case 3 -> Priority.HIGH;
            default -> Priority.MIDDLE;
        };
    }
}