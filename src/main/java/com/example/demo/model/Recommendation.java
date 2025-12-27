
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table(name = "user_recommendations") // Renamed to avoid DB ghost constraints
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Recommendation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    private String recommendedLessonIds; // Stores IDs as "1,2,5"
    private String basisSnapshot;        // Stores JSON reason (e.g. "Completed Java Basics")
    private BigDecimal confidenceScore;
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
        if (this.confidenceScore == null) this.confidenceScore = BigDecimal.valueOf(0.9);
    }
}