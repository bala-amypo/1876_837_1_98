// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.Recommendation;
// import com.example.demo.model.User;
// import com.example.demo.repository.RecommendationRepository;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.RecommendationService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// @Transactional
// public class RecommendationServiceImpl implements RecommendationService {

//     private final RecommendationRepository repository;
//     private final UserRepository userRepository;

//     public RecommendationServiceImpl(RecommendationRepository repository,
//                                      UserRepository userRepository) {
//         this.repository = repository;
//         this.userRepository = userRepository;
//     }

//     @Override
//     public Recommendation generateRecommendation(Long userId, String basisSnapshot, double confidenceScore) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         Recommendation recommendation = Recommendation.builder()
//                 .generatedAt(LocalDateTime.now())
//                 .basisSnapshot(basisSnapshot)
//                 .confidenceScore(BigDecimal.valueOf(confidenceScore))
//                 .user(user)
//                 .build();

//         return repository.save(recommendation);
//     }

//     @Override
//     public Recommendation getLatest(Long userId) {
//         return repository.findTopByUserIdOrderByGeneratedAtDesc(userId)
//                 .orElseThrow(() -> new ResourceNotFoundException("No recommendations found"));
//     }

//     @Override
//     public List<Recommendation> getByUser(Long userId) {
//         return repository.findByUserId(userId);
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, 
                                   UserRepository userRepository, 
                                   MicroLessonRepository microLessonRepository,
                                   ProgressRepository progressRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
        this.progressRepository = progressRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<Progress> userProgress = progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
        List<Long> completedLessonIds = userProgress.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .map(p -> p.getMicroLesson().getId())
                .collect(Collectors.toList());
        
        List<MicroLesson> candidateLessons = microLessonRepository.findByFilters(
                params.getTags(), 
                params.getDifficulty(), 
                params.getContentType()
        );
        
        List<MicroLesson> recommendedLessons = candidateLessons.stream()
                .filter(lesson -> !completedLessonIds.contains(lesson.getId()))
                .limit(params.getLimit() != null ? params.getLimit() : 5)
                .collect(Collectors.toList());
        
        String lessonIds = recommendedLessons.stream()
                .map(lesson -> lesson.getId().toString())
                .collect(Collectors.joining(","));
        
        String basisSnapshot = String.format(
                "{\"completedLessons\":%d,\"filters\":{\"tags\":\"%s\",\"difficulty\":\"%s\",\"contentType\":\"%s\"}}",
                completedLessonIds.size(),
                params.getTags() != null ? params.getTags() : "",
                params.getDifficulty() != null ? params.getDifficulty() : "",
                params.getContentType() != null ? params.getContentType() : ""
        );
        
        BigDecimal confidenceScore = calculateConfidenceScore(recommendedLessons.size(), userProgress.size());
        
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(lessonIds)
                .basisSnapshot(basisSnapshot)
                .confidenceScore(confidenceScore)
                .build();
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found");
        }
        return recommendations.get(0);
    }

    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }

    private BigDecimal calculateConfidenceScore(int recommendedCount, int userProgressCount) {
        if (recommendedCount == 0) {
            return BigDecimal.ZERO;
        }
        
        double baseScore = Math.min(recommendedCount / 5.0, 1.0);
        double progressBonus = Math.min(userProgressCount / 10.0, 0.3);
        
        return BigDecimal.valueOf(Math.min(baseScore + progressBonus, 1.0))
                .setScale(2, RoundingMode.HALF_UP);
    }
}