package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;
    private final ProgressRepository progressRepository;

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MicroLesson> lessons = lessonRepository.findByTagsContainingAndDifficultyAndContentType(
                params.getTags(), params.getDifficulty(), params.getContentType());

        String recommendedIds = lessons.stream()
                .limit(params.getLimit() == null ? 5 : params.getLimit())
                .map(l -> String.valueOf(l.getId()))
                .collect(Collectors.joining(","));

        Recommendation rec = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedIds)
                .basisSnapshot("Generated based on filters and progress")
                .confidenceScore(BigDecimal.valueOf(Math.random()))
                .build();

        return recommendationRepository.save(rec);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> list = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        if (list.isEmpty()) throw new ResourceNotFoundException("No recommendations found");
        return list.get(0);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59);
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
