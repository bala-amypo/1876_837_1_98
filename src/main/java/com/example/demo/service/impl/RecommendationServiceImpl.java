package com.example.demo.service.impl;

import com.example.demo.model.Recommendation;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationServiceImpl {
    private final RecommendationRepository recRepo;
    private final UserRepository userRepo;
    private final MicroLessonRepository lessonRepo;

    public RecommendationServiceImpl(RecommendationRepository rr, UserRepository ur, MicroLessonRepository lr) {
        this.recRepo = rr;
        this.userRepo = ur;
        this.lessonRepo = lr;
    }

    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> list = recRepo.findByUserIdOrderByGeneratedAtDesc(userId);
        if (list.isEmpty()) throw new RuntimeException("No recommendations");
        return list.get(0);
    }
}