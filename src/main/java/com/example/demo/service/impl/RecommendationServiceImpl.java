package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final RecommendationRepository recommendationRepository;

    @Override
    public Recommendation generateRecommendation(RecommendationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Simple rule-based recommendation (can be upgraded to ML later)
        List<MicroLesson> lessons = microLessonRepository
                .findByTagsContainingAndDifficultyAndContentType(
                        request.getPreferredCategory(),
                        request.getDifficultyLevel(),
                        "VIDEO"
                );

        String details = lessons.stream()
                .map(MicroLesson::getTitle)
                .collect(Collectors.joining(", "));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendationDetails(details)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    @Override
    public List<Recommendation> getRecommendationsForUser(Long userId) {
        return recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
    }
}
