package com.example.demo.dto;
import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class RecommendationRequest {
    private String tags;
    private String difficulty;
    private String contentType;
}