package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 🔥 THIS IS CRITICAL
public class RecommendationServiceImpl implements RecommendationService {

    private final MicroLessonRepository microLessonRepository;

    public RecommendationServiceImpl(MicroLessonRepository microLessonRepository) {
        this.microLessonRepository = microLessonRepository;
    }

    @Override
    public List<MicroLesson> recommendLessons(
            Long userId,
            String difficulty,
            String contentType
    ) {
        return microLessonRepository
                .findByDifficultyAndContentType(difficulty, contentType);
    }
}
