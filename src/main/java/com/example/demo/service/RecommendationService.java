package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface RecommendationService {

    List<MicroLesson> recommendLessons(
            Long userId,
            String difficulty,
            String contentType
    );
}
