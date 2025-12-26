// package com.example.demo.model;
// import jakarta.persistence.*;
// import lombok.*;

// @Entity @Table(name = "micro_lessons")
// @Data @Builder @NoArgsConstructor @AllArgsConstructor
// public class MicroLesson {
//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @ManyToOne @JoinColumn(name = "course_id")
//     private Course course; // This is the field LessonServiceImpl uses setCourse() on

//     private String title;
//     private Integer durationMinutes;
//     private String contentType;
//     private String difficulty;
//     private String tags;
// }
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String title;

    private Integer durationMinutes;

    private String contentType;

    private String difficulty;

    private String tags;

    private LocalDate publishDate;
}
