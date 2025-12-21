// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.RecommendationService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// @Transactional
// public class RecommendationServiceImpl implements RecommendationService {

//     private final RecommendationRepository recommendationRepository;
//     private final ProgressRepository progressRepository;
//     private final MicroLessonRepository lessonRepository;
//     private final UserRepository userRepository;

//     public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
//                                      ProgressRepository progressRepository,
//                                      MicroLessonRepository lessonRepository,
//                                      UserRepository userRepository) {
//         this.recommendationRepository = recommendationRepository;
//         this.progressRepository = progressRepository;
//         this.lessonRepository = lessonRepository;
//         this.userRepository = userRepository;
//     }

//     @Override
//     public Recommendation generateRecommendation(Long userId) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         // Example: Recommend first 5 lessons not completed
//         List<MicroLesson> lessons = lessonRepository.findAll().stream()
//                 .limit(5)
//                 .toList();

//         String lessonIds = lessons.stream()
//                 .map(l -> String.valueOf(l.getId()))
//                 .reduce((a, b) -> a + "," + b)
//                 .orElse("");

//         Recommendation rec = Recommendation.builder()
//                 .user(user)
//                 .recommendedLessonIds(lessonIds)
//                 .confidenceScore(BigDecimal.valueOf(0.8))
//                 .basisSnapshot("{\"rule\": \"Simple top 5 suggestion\"}")
//                 .build();

//         return recommendationRepository.save(rec);
//     }

//     @Override
//     public Recommendation getLatestRecommendation(Long userId) {
//         List<Recommendation> list = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
//         return list.isEmpty() ? null : list.get(0);
//     }

//     @Override
//     public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
//         LocalDateTime start = from.atStartOfDay();
//         LocalDateTime end = to.plusDays(1).atStartOfDay();
//         return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MicroLesson> lessons =
                microLessonRepository.findByTagsContainingAndDifficultyAndContentType(
                        request.getTags(),
                        request.getDifficulty(),
                        request.getContentType()
                );

        if (lessons.isEmpty()) {
            throw new RuntimeException("No lessons found for recommendation");
        }

        String lessonIds = lessons.stream()
                .map(l -> l.getId().toString())
                .collect(Collectors.joining(","));

        BigDecimal confidenceScore = calculateConfidenceScore(user, lessons);

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(lessonIds)
                .basisSnapshot(buildBasisSnapshot(user, request))
                .confidenceScore(confidenceScore)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        return recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No recommendations found"));
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return recommendationRepository
                .findByUserIdAndGeneratedAtBetween(userId, start, end);
    }

    private BigDecimal calculateConfidenceScore(User user, List<MicroLesson> lessons) {

        long completedCount = progressRepository.findAll().stream()
                .filter(p ->
                        p.getUser().getId().equals(user.getId()) &&
                        "COMPLETED".equals(p.getStatus()))
                .count();

        if (completedCount == 0) {
            return BigDecimal.valueOf(0.50);
        }

        return BigDecimal.valueOf(completedCount)
                .divide(BigDecimal.valueOf(lessons.size()), 2, RoundingMode.HALF_UP)
                .min(BigDecimal.ONE);
    }

    private String buildBasisSnapshot(User user, RecommendationRequest request) {
        return String.format(
                "{ \"user\": \"%s\", \"difficulty\": \"%s\", \"contentType\": \"%s\", \"tags\": \"%s\" }",
                user.getEmail(),
                request.getDifficulty(),
                request.getContentType(),
                request.getTags()
        );
    }
}
