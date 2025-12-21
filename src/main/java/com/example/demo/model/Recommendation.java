package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(max = 1000)
    private String recommendedLessonIds; // comma-separated IDs

    @Size(max = 2000)
    private String basisSnapshot; // JSON snapshot of criteria

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private BigDecimal confidenceScore;

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
}
