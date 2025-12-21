package com.example.demo.service;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import java.util.List;

public interface RecommendationService {
    Recommendation generateRecommendation(RecommendationRequest request);
    List<Recommendation> getRecommendationsForUser(Long userId);
}
