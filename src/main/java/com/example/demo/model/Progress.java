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

    /**
     * Progress status:
     * NOT_STARTED, IN_PROGRESS, COMPLETED
     */
    @Column(nullable = false)
    private String status;

    /**
     * Completion percentage (0–100)
     */
    @Column(nullable = false)
    private Integer progressPercent;

    /**
     * Learner score for quizzes/assessments
     */
    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    /**
     * Last time the lesson was accessed
     */
    private LocalDateTime lastAccessedAt;

    /**
     * User associated with this progress
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Micro lesson associated with this progress
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    /**
     * Automatically set timestamps
     */
    @PrePersist
    public void onCreate() {
        this.lastAccessedAt = LocalDateTime.now();
        if (this.progressPercent == null) {
            this.progressPercent = 0;
        }
        if (this.status == null) {
            this.status = "NOT_STARTED";
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}
