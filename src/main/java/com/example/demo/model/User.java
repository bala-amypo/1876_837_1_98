package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    private String role = "LEARNER"; // LEARNER, INSTRUCTOR, ADMIN

    private String preferredLearningStyle; // VIDEO, TEXT, etc.

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    private List<Progress> progresses;

    @OneToMany(mappedBy = "user")
    private List<Recommendation> recommendations;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (role == null) {
            role = "LEARNER";
        }
    }
}
