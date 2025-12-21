package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

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

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    private String preferredLearningStyle;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Progress> progress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (role == null) role = "LEARNER";
    }
}
