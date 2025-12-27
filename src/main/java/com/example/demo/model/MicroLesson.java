

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity 
@Table(name = "course_lessons") 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MicroLesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This ensures it links to the 'courses' table from your screenshot
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) 
    private Course course;

    private String title;
    private Integer durationMinutes;
    private String contentType; 
    private String difficulty;  
    private String tags;        
    private LocalDate publishDate;
}