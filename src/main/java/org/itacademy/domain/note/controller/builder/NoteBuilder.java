package org.itacademy.domain.note.controller.builder;

import org.itacademy.domain.note.exception.DescriptionInBlankException;
import org.itacademy.domain.note.model.Note;
import org.itacademy.input.InputReader;

public class NoteBuilder {
    private final InputReader scanner;
    private String description;
    private Long taskId;

    public NoteBuilder(InputReader scanner) {
        this.scanner = scanner;
    }

    public NoteBuilder withDescription() {
        while (true) {
            try {
                String inputDescription = scanner.readString("Please, write the description of the note");

                if (inputDescription == null || inputDescription.isBlank()) {
                    throw new DescriptionInBlankException("Error: The description cannot be empty or blank.");
                }

                this.description = inputDescription;
                return this;
            } catch (DescriptionInBlankException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public NoteBuilder withTaskId() {
        this.taskId = readValidTaskId();
        return this;
    }

    public Note build() {
        return new Note(description, taskId);
    }

    private Long readValidTaskId() {
        while (true) {
            int value = scanner.readInt("Please, enter the task id related to this note: ");

            if (value<= 0) {
                System.out.println("Task id must be greater than 0.");
            } else {
                return (long) value;
            }
        }
    }
}