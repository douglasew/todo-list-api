package com.todolist.api.exceptions;

public class TaskDoesNotExistException extends RuntimeException{

    public TaskDoesNotExistException() {
        super("A tarefa não existe");
    }

    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
