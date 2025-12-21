package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime generatedAt;

    @Column(length = 2000)
    private String recommendedLessonIds; // comma-separated lesson IDs

    @Column(length = 4000)
    private String basisSnapshot; // JSON/text

    private BigDecimal confidenceScore; // 0.0–1.0

    @PrePersist
    public void prePersist() {
        generatedAt = LocalDateTime.now();
        if (confidenceScore == null) {
            confidenceScore = BigDecimal.ZERO;
        }
    }
}
