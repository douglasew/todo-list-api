package com.todolist.api.domain.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.todolist.api.domain.priority.Priority;
import com.todolist.api.domain.status.Status;
import com.todolist.api.domain.user.User;
import com.todolist.api.dtos.TaskRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String description;

    @Column(name = "end_at")
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Task(TaskRequestDTO taskRequestDTO) {
        this.title = taskRequestDTO.getTitle();
        this.description = taskRequestDTO.getDescription();
        this.priority = taskRequestDTO.getPriority();
        this.endAt = taskRequestDTO.getEndAt();
        this.user = taskRequestDTO.getUser();
        this.status = taskRequestDTO.getStatus();
    }
}
