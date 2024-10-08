package com.todolist.api.domain.priority;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "priorities")
public class Priority {
    @Id
    private Long id;
    private String name;

    public enum Values {
        LOW(1L, "LOW"),
        MEDIUM(2L, "MEDIUM"),
        HIGH(3L, "HIGH"),
        URGENT(4L, "URGENT");

        private Long id;
        private String name;

        Values(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Priority toPriority() {
            return new Priority(id, name);
        }
    }
}
