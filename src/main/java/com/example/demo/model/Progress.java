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
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "micro_lesson_id")
    private MicroLesson microLesson;

    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED

    private Integer progressPercent;

    private LocalDateTime lastAccessedAt;

    private BigDecimal score;

    @PrePersist
    public void prePersist() {
        this.lastAccessedAt = LocalDateTime.now();
        if ("COMPLETED".equals(status)) {
            this.progressPercent = 100;
        }
    }
}
