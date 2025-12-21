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

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;
    private final RecommendationRepository recommendationRepository;

    @Override
    public Recommendation generateRecommendation(Long userId,
                                                   String difficulty,
                                                   String contentType,
                                                   String tags) {

        // 1. Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Fetch matching lessons
        List<MicroLesson> lessons =
                microLessonRepository.findByTagsContainingAndDifficultyAndContentType(
                        tags, difficulty, contentType
                );

        if (lessons.isEmpty()) {
            throw new RuntimeException("No lessons found for recommendation");
        }

        // 3. Build recommended lesson IDs (CSV / JSON-like)
        String recommendedLessonIds = lessons.stream()
                .map(lesson -> lesson.getId().toString())
                .collect(Collectors.joining(",", "[", "]"));

        // 4. Calculate confidence score
        BigDecimal confidenceScore = calculateConfidenceScore(user, lessons);

        // 5. Create recommendation
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedLessonIds)
                .basisSnapshot(buildBasisSnapshot(user, difficulty, contentType, tags))
                .confidenceScore(confidenceScore)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationRepository.save(recommendation);
    }

    /**
     * Calculates confidence score between 0.00 and 1.00
     */
    private BigDecimal calculateConfidenceScore(User user, List<MicroLesson> lessons) {

        List<Progress> completedProgress =
                progressRepository.findAll().stream()
                        .filter(p ->
                                p.getUser().getId().equals(user.getId()) &&
                                "COMPLETED".equals(p.getStatus()))
                        .collect(Collectors.toList());

        if (completedProgress.isEmpty()) {
            return BigDecimal.valueOf(0.50).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal completedCount = BigDecimal.valueOf(completedProgress.size());
        BigDecimal lessonCount = BigDecimal.valueOf(lessons.size());

        return completedCount
                .divide(lessonCount, 2, RoundingMode.HALF_UP)
                .min(BigDecimal.ONE);
    }

    /**
     * Creates a snapshot of recommendation conditions
     */
    private String buildBasisSnapshot(User user,
                                      String difficulty,
                                      String contentType,
                                      String tags) {

        return String.format(
                "User:%s | Role:%s | Difficulty:%s | ContentType:%s | Tags:%s",
                user.getEmail(),
                user.getRole(),
                difficulty,
                contentType,
                tags
        );
    }
}
