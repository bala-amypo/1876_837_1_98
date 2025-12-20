package com.example.demo.service;

import com.example.demo.model.Recommendation;

import java.util.List;

public interface RecommendationService {
    List<Recommendation> getRecommendations(Long userId);
}
