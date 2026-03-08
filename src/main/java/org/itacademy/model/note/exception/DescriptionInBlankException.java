package org.itacademy.model.note.exception;

public class DescriptionInBlankException extends RuntimeException {
    public DescriptionInBlankException() {
        super("The description cannot be blanck");
    }
}
