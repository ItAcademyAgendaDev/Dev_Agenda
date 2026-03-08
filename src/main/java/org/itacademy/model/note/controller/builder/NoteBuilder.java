package org.itacademy.model.note.controller.builder;

import org.itacademy.input.InputReader;
import org.itacademy.model.note.exception.DescriptionInBlankException;
import org.itacademy.model.note.model.Note;

public class NoteBuilder {
    private final InputReader scanner;
    private String description;
    private Long taskId;

    public NoteBuilder(InputReader scanner) {
        this.scanner = scanner;
    }

    public NoteBuilder withDescription() {
        while (true) {
            String value = scanner.readString("Please, write the note description: ");
            if (value.isBlank()) {
                System.out.println(new DescriptionInBlankException().getMessage());
            } else {
                this.description = value;
                return this;
            }
        }
    }

    public NoteBuilder withTaskId() {
        this.taskId = scanner.readLong("Please, enter the task id related to this note: ");
        return this;
    }

    public Note build() {
        return new Note(description, taskId);
    }
}