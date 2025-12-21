package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl {

    private final MicroLessonRepository microLessonRepository;

    public RecommendationServiceImpl(MicroLessonRepository repo) {
        this.microLessonRepository = repo;
    }

    public List<Long> recommend(User user) {

        List<MicroLesson> lessons =
                microLessonRepository
                        .findByTagsContainingAndDifficultyAndContentType(
                                "java",
                                "BEGINNER",
                                "VIDEO"
                        );

        return lessons.stream()
                .map(MicroLesson::getId)
                .collect(Collectors.toList());
    }
}
