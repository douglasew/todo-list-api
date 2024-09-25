package com.todolist.api.dtos;

import java.time.LocalDate;

import com.todolist.api.domain.priority.Priority;
import com.todolist.api.domain.status.Status;
import com.todolist.api.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {

    @NotEmpty(message = "Insira o título")
    @Size(max = 50,message = "Título muito longo")
    private String title;

    @NotEmpty(message = "Insira a descrição")
    @Size(max = 180, message = "Descrição muito longa")
    private String description;

    @NotNull(message = "Insira a data de término")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate endAt;

    @NotNull(message = "Insira a prioridade")
    private Priority priority;

    //@ValidEnum(enumClass = Status.class, message = "Invalid status value")
    private Status status;

    private User user;
}
