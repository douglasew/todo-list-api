package com.todolist.api.domain.role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    private Long id;
    private String name;

    public enum Values {
        ADMIN(1L, "ADMIN"),
        USER(2L, "USER");

        private Long id;
        private String name;

        Values(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Role toRole() {
            return new Role(id, name);
        }
    }
}
