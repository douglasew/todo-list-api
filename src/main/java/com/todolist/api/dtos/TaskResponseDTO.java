package com.todolist.api.dtos;

import com.todolist.api.enums.Status;

import java.time.LocalDate;

public record TaskResponseDTO(String id, String title, String description, String priority, LocalDate startAt, LocalDate endAt, Status status) {
}
