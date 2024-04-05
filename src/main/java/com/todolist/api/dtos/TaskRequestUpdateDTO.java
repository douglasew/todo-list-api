package com.todolist.api.dtos;

import com.todolist.api.domain.priority.Priority;
import java.sql.Timestamp;

public record TaskRequestUpdateDTO(String title, String description, Timestamp startAt, Timestamp endAt, Priority priority) {
}
