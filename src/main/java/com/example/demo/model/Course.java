package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    private LocalDate createdAt;

    // 👇 ONLY instructor ID will be shown, not full user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @JsonIgnoreProperties({"courses", "progresses", "recommendations", "password"})
    private User instructor;

    // 👇 Avoid infinite recursion
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MicroLesson> lessons;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }
}
