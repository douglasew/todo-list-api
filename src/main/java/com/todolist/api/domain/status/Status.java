package com.todolist.api.domain.status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    private Long id;
    private String name;

    public enum Values {
        PENDING(1L, "PENDING"),
        SUCCESS(2L, "SUCCESS"),
        DELAYED(3L, "DELAYED"),
        CANCELED(4L, "CANCELED");

        private Long id;
        private String name;

        Values(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Status toStatus() {
            return new Status(id, name);
        }
    }
}
