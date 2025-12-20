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

    @ManyToOne
    private User user;

    private String recommendedLessonIds;

    private BigDecimal confidenceScore;

    private LocalDateTime generatedAt;

    @PrePersist
    void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}
