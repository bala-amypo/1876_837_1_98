package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RecommendationResponse {

    private Long id;
    private LocalDateTime generatedAt;
    private List<Long> recommendedLessonIds;
    private String basisSnapshot;
    private double confidenceScore;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public List<Long> getRecommendedLessonIds() { return recommendedLessonIds; }
    public void setRecommendedLessonIds(List<Long> recommendedLessonIds) { this.recommendedLessonIds = recommendedLessonIds; }

    public String getBasisSnapshot() { return basisSnapshot; }
    public void setBasisSnapshot(String basisSnapshot) { this.basisSnapshot = basisSnapshot; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }
}
