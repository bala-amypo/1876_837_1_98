package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String difficulty;

    private String tags;
    private LocalDate publishDate;

    // 👇 Prevent recursion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL)
    private List<Progress> progressList;
}
