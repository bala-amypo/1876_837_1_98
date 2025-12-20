package com.example.demo.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String tags;                // comma-separated
    private String difficulty;          // BEGINNER / INTERMEDIATE / ADVANCED
    private String contentType;         // VIDEO / TEXT / QUIZ / etc.
    private Integer limit;              // number of recommendations
    private String preferredLearningStyle;
}
