package com.todolist.api.repositories;

import com.todolist.api.domain.task.Task;
import com.todolist.api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findByUser(User user, Pageable pageable);

    @Transactional
    @Modifying
    @Query(
        "UPDATE Task t " +
        "SET t.status.id = 3 " +
        "WHERE :currentDate > t.endAt " +
        "AND t.status.id = 1"
    )
    void markLateTasksAsCompleted(@Param("currentDate") LocalDate currentDate);
}
