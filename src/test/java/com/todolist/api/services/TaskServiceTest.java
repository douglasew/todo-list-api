package com.todolist.api.services;

import com.todolist.api.domain.priority.Priority;
import com.todolist.api.dtos.TaskRequestDTO;
import com.todolist.api.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createSuccess() {
        LocalDate date = LocalDate.now();
        LocalDate startAt = date.plusDays(2);
        LocalDate endAt = startAt.plusDays(3);

        TaskRequestDTO task = new TaskRequestDTO();
        task.setTitle("new");
        task.setDescription("new task");
        //task.setStartAt(startAt);
        //task.setEndAt(endAt);
        task.setPriority(new Priority(1L, "NORMAL"));
        System.out.println(startAt);
    }

    @Test
    void createFailure(){
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
