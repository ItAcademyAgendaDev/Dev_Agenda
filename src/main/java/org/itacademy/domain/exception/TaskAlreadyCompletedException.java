package org.itacademy.domain.exception;

public class TaskAlreadyCompletedException extends AgendaException {
    public TaskAlreadyCompletedException(String message) {
        super(message);
    }
}
