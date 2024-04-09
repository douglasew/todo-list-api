package com.todolist.api.repositories;

import com.todolist.api.domain.task.Task;
import com.todolist.api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findByUser(User user, Pageable pageable);
}
