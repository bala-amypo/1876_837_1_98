// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.math.BigDecimal;
// import java.time.LocalDateTime;

// @Entity @Table(name = "progress")
// @Data @NoArgsConstructor @AllArgsConstructor @Builder
// public class Progress {
//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @ManyToOne @JoinColumn(name = "user_id")
//     private User user;
//     @ManyToOne @JoinColumn(name = "micro_lesson_id")
//     private MicroLesson microLesson;
//     private String status; // IN_PROGRESS, COMPLETED
//     private Integer progressPercent;
//     private LocalDateTime lastAccessedAt;
//     private BigDecimal score;

//     @PrePersist
//     public void prePersist() {
//         this.lastAccessedAt = LocalDateTime.now();
//     }
// }


// test case came new table
// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.math.BigDecimal;
// import java.time.LocalDateTime;

// @Entity @Table(name = "progress")
// @Data @Builder @NoArgsConstructor @AllArgsConstructor
// public class Progress {
//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @ManyToOne @JoinColumn(name = "user_id")
//     private User user;
//     @ManyToOne @JoinColumn(name = "micro_lesson_id")
//     private MicroLesson microLesson;
//     private String status;
//     private Integer progressPercent;
//     private LocalDateTime lastAccessedAt;
//     private BigDecimal score;

//     @PrePersist
//     public void prePersist() { this.lastAccessedAt = LocalDateTime.now(); }
// }


package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table(name = "user_progress_data") // CHANGED: Bypasses the broken 'progress' table
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Progress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    // This link was broken because it was looking for 'micro_lessons'
    @ManyToOne @JoinColumn(name = "micro_lesson_id")
    private MicroLesson microLesson;

    private String status; 
    private Integer progressPercent;
    private BigDecimal score;
    private LocalDateTime lastAccessedAt;

    @PrePersist
    public void prePersist() {
        this.lastAccessedAt = LocalDateTime.now();
        // Technical Rule: If COMPLETED, percent must be 100
        if ("COMPLETED".equals(this.status)) {
            this.progressPercent = 100;
        }
    }
}