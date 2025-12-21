package com.example.demo.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String tags;
    private String difficulty;
    private Integer maxItems;
}
