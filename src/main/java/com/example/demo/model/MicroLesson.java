package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "micro_lessons")
@Data
@NoArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    private String title;

    private Integer durationMinutes;

    private String contentType; // VIDEO, TEXT

    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED

    private String tags;

    private LocalDate publishDate;
}
