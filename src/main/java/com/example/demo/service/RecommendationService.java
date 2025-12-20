package com.example.demo.service;

import com.example.demo.model.Recommendation;   // ✅ REQUIRED
import java.util.List;

public interface RecommendationService {

    Recommendation generateRecommendation(Long userId);

    List<Recommendation> getRecommendations(Long userId);
}
