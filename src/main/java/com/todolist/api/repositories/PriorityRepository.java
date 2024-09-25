package com.todolist.api.repositories;

import com.todolist.api.domain.priority.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}