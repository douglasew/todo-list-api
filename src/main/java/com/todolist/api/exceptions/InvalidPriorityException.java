package com.todolist.api.exceptions;

public class InvalidPriorityException extends RuntimeException {

    public InvalidPriorityException() {
        super("Prioridade não encontrada");
    }

    public InvalidPriorityException(String message) {
        super(message);
    }
}
