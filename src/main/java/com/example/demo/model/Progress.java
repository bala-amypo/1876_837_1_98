package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED
    private Integer progressPercent; // 0-100
    private LocalDateTime lastAccessedAt;
    private BigDecimal score;

    @PrePersist
    public void prePersist() {
        lastAccessedAt = LocalDateTime.now();
    }
}
