package com.todolist.api.repositories;

import com.todolist.api.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}