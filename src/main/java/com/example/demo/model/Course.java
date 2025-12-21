package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;
    private String category;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
