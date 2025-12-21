package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "micro_lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer durationMinutes;

    private String contentType; // VIDEO / TEXT

    private String difficulty; // BEGINNER / INTERMEDIATE / ADVANCED

    private String tags; // comma-separated

    private LocalDate publishDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "microLesson")
    private List<Progress> progressList;
}
