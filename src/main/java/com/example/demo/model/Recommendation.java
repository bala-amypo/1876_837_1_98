package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
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
    private String recommendedLessonIds; // JSON array of lesson IDs
    private String basisSnapshot;
    private Double confidenceScore;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
