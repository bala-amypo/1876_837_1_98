package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "micro_lessons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false, length = 50)
    private String contentType;  // VIDEO, TEXT, etc.

    @Column(nullable = false, length = 50)
    private String difficulty;   // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(length = 500)
    private String tags;         // Comma-separated tags

    private LocalDate publishDate;

    @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Progress> progresses;
}
