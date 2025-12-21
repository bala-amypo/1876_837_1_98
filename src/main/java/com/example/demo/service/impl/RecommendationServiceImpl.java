package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository repository;
    private final UserRepository userRepository;

    public RecommendationServiceImpl(RecommendationRepository repository,
                                     UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, String basisSnapshot, double confidenceScore) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Recommendation recommendation = Recommendation.builder()
                .generatedAt(LocalDateTime.now())
                .basisSnapshot(basisSnapshot)
                .confidenceScore(BigDecimal.valueOf(confidenceScore))
                .user(user)
                .build();

        return repository.save(recommendation);
    }

    @Override
    public Recommendation getLatest(Long userId) {
        return repository.findTopByUserIdOrderByGeneratedAtDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No recommendations found"));
    }

    @Override
    public List<Recommendation> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
