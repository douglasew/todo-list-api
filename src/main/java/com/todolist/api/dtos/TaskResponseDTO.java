package com.todolist.api.dtos;

import java.sql.Timestamp;

public record TaskResponseDTO(String id, String title, String description, String priority, Timestamp startAt, Timestamp endAt) {
}
