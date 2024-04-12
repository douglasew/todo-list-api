package com.todolist.api.dtos;

import java.time.LocalDate;

import com.todolist.api.domain.priority.Priority;
import com.todolist.api.domain.user.User;
import com.todolist.api.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TaskRequestDTO {

    @NotEmpty(message = "Enter the title")
    @Size(max = 50,message = "Very long title")
    private String title;

    @NotEmpty(message = "Enter the description")
    @Size(max = 180, message = "Very long description")
    private String description;

    @NotNull(message = "Enter the start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate startAt;

    @NotNull(message = "Enter the end date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate endAt;

    @NotNull(message = "Enter the priority")
    private Priority priority;

    private Status status;

    private User user;
}
