package com.example.demo.service;

import com.example.demo.model.Recommendation;
import java.util.List;

public interface RecommendationService {

    Recommendation generateRecommendation(Long userId, String basisSnapshot, double confidenceScore);

    Recommendation getLatest(Long userId);

    List<Recommendation> getByUser(Long userId);
}
