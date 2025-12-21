package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final ProgressRepository progressRepository;
    private final MicroLessonRepository lessonRepository;
    private final UserRepository userRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
                                     ProgressRepository progressRepository,
                                     MicroLessonRepository lessonRepository,
                                     UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.progressRepository = progressRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Example: Recommend first 5 lessons not completed
        List<MicroLesson> lessons = lessonRepository.findAll().stream()
                .limit(5)
                .toList();

        String lessonIds = lessons.stream()
                .map(l -> String.valueOf(l.getId()))
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        Recommendation rec = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(lessonIds)
                .confidenceScore(BigDecimal.valueOf(0.8))
                .basisSnapshot("{\"rule\": \"Simple top 5 suggestion\"}")
                .build();

        return recommendationRepository.save(rec);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> list = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay();
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}
