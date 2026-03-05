package org.itacademy.model.task.exception;

public class TitleInBlankException extends RuntimeException {
    public TitleInBlankException() {
        super("The title cannot be in blank");
    }
}
