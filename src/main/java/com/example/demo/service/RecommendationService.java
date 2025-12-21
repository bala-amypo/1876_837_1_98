package com.example.demo.service;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;

import java.util.List;

public interface RecommendationService {

    Recommendation generate(Long userId, RecommendationRequest request);

    Recommendation getLatest(Long userId);

    List<Recommendation> getByUser(Long userId);
}
