package org.itacademy.domain.task.controller.builder;

import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.exception.InvalidDateException;
import org.itacademy.domain.task.exception.TitleInBlankException;
import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;
import org.itacademy.input.InputReader;

import java.time.LocalDate;

public class TaskBuilder {

    private final InputReader scanner;

    private String title;
    private String description;
    private LocalDate deadline;
    private Priority priority = Priority.MIDDLE;
    private Long eventId;

    public TaskBuilder(InputReader scanner) {
        this.scanner = scanner;
    }

    public TaskBuilder withTitle() {

        while (true) {
            try {

                String inputTitle = scanner.readString("Please write the task title:");

                if (inputTitle == null || inputTitle.isBlank()) {
                    throw new TitleInBlankException("Error: Title cannot be empty.");
                }

                this.title = inputTitle.trim();
                return this;

            } catch (TitleInBlankException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public TaskBuilder withDescription() {

        if (scanner.readYesNo("Do you want to add a description?")) {
            this.description = scanner.readString("Write the description:");
        }

        return this;
    }

    public TaskBuilder withDeadline() {

        if (scanner.readYesNo("Does the task have a deadline?")) {
            this.deadline = readValidDate();
        }

        return this;
    }

    public TaskBuilder withPriority() {

        if (scanner.readYesNo("Do you want to define the priority?")) {
            this.priority = readPriority();
        }

        return this;
    }

    public TaskBuilder withEvent(Long id ){
        this.eventId = id;
        return this;
    }

    public TaskDtoRequest build() {

        return new TaskDtoRequest(
                title,
                description,
                deadline,
                priority,
                Status.NOT_COMPLETED,
                eventId
        );
    }

    private LocalDate readValidDate() {

        while (true) {

            try {

                System.out.println("--- Enter Deadline ---");

                int day = scanner.readInt("Day:");
                int month = scanner.readInt("Month:");
                int year = scanner.readInt("Year:");

                LocalDate date = LocalDate.of(year, month, day);

                if (date.isBefore(LocalDate.now())) {
                    throw new InvalidDateException("Date cannot be in the past.");
                }

                return date;

            } catch (java.time.DateTimeException e) {
                System.out.println("Invalid date. That day does not exist.");
            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Priority readPriority() {

        int option = scanner.readInt("""
                Choose priority:
                1. LOW
                2. MIDDLE
                3. HIGH
                """);

        return switch (option) {
            case 1 -> Priority.LOW;
            case 3 -> Priority.HIGH;
            default -> Priority.MIDDLE;
        };
    }
}
