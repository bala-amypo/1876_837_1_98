package com.example.demo.service;

import com.example.demo.model.Recommendation;
import com.example.demo.dto.RecommendationRequest;

import java.time.LocalDate;
import java.util.List;

public interface RecommendationService {
    Recommendation generateRecommendation(Long userId, RecommendationRequest params);
    Recommendation getLatestRecommendation(Long userId);
    List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to);
}
