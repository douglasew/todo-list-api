package com.todolist.api.enums;

public enum Status {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DELAYED("Delayed"),
    CANCELLED("Cancelled");

    private String status;

    Status(String status){
        this.status = status;
    }
}
