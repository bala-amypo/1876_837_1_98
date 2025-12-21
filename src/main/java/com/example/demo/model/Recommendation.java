package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime generatedAt;

    @Column(columnDefinition = "TEXT")
    private String recommendedLessonIds;

    @Column(columnDefinition = "TEXT")
    private String basisSnapshot;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal confidenceScore;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
}

