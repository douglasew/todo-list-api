package com.todolist.api.dtos;


import java.time.LocalDate;

public record TaskResponseDTO(String id, String title, String description, String priority, LocalDate endAt, String status) {
}
