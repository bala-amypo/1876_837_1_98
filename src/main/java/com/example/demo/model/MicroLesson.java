package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

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

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotNull
    @Positive
    @Max(15)
    private Integer durationMinutes;

    @NotBlank
    @Size(max = 50)
    private String contentType; // VIDEO, ARTICLE, QUIZ, INTERACTIVE

    @NotBlank
    @Size(max = 50)
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED

    @Size(max = 500)
    private String tags; // comma-separated

    @NotNull
    private LocalDate publishDate;
}
