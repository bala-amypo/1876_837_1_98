package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository repository;

    public RecommendationServiceImpl(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation generate(Long userId, RecommendationRequest request) {
        Recommendation rec = new Recommendation();
        rec.setGeneratedAt(LocalDateTime.now());
        rec.setRecommendedLessonIds("1,2,3");
        rec.setBasisSnapshot(request.toString());
        rec.setConfidenceScore(0.85);

        return repository.save(rec);
    }

    @Override
    public Recommendation getLatest(Long userId) {
        return repository.findTopByUserIdOrderByGeneratedAtDesc(userId);
    }

    @Override
    public List<Recommendation> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
