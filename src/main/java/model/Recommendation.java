package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private String recommendedLessonIds; // comma-separated lesson ids

    private String basisSnapshot; // JSON/text

    @Column(nullable = false)
    private BigDecimal confidenceScore;

    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
    }
}
