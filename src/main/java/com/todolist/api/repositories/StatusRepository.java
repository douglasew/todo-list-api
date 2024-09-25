package com.todolist.api.repositories;

import com.todolist.api.domain.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}