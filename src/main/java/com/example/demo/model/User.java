package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // STUDENT / INSTRUCTOR / ADMIN

    private String preferredLearningStyle;

    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDate.now();
    }
}
