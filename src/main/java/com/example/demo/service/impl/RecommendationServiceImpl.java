package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Example: select lessons by difficulty and tags
        List<MicroLesson> candidates = lessonRepository.findByFilters(
                params.getTags(),
                params.getDifficulty(),
                params.getContentType()
        );

        String recommendedLessonIds = candidates.stream()
                .map(l -> String.valueOf(l.getId()))
                .collect(Collectors.joining(","));

        String basisSnapshot = "{ \"difficulty\": \"" + params.getDifficulty() +
                "\", \"tags\": \"" + params.getTags() + "\" }";

        BigDecimal confidenceScore = BigDecimal.valueOf(Math.min(1.0, candidates.size() / 10.0));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedLessonIds)
                .basisSnapshot(basisSnapshot)
                .confidenceScore(confidenceScore)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for user");
        }
        return recommendations.get(0);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDateTime from, LocalDateTime to) {
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, from, to)
                .stream()
                .sorted(Comparator.comparing(Recommendation::getGeneratedAt).reversed())
                .collect(Collectors.toList());
    }
}
