package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "micro_lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private String contentType; // VIDEO, TEXT

    @Column(nullable = false)
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED

    private String tags; // comma-separated

    private LocalDate publishDate;

    @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL)
    private List<Progress> progresses;
}
