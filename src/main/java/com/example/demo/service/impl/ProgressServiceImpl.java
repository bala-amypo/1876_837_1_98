package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgressServiceImpl {
    private final ProgressRepository progressRepo;
    private final UserRepository userRepo;
    private final MicroLessonRepository lessonRepo;

    public ProgressServiceImpl(ProgressRepository pr, UserRepository ur, MicroLessonRepository lr) {
        this.progressRepo = pr;
        this.userRepo = ur;
        this.lessonRepo = lr;
    }

    public Progress recordProgress(Long userId, Long lessonId, Progress incoming) {
        User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        MicroLesson m = lessonRepo.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson not found"));

        Progress p = progressRepo.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(Progress.builder().user(u).microLesson(m).build());

        p.setProgressPercent(incoming.getProgressPercent());
        p.setStatus(incoming.getStatus());
        p.setScore(incoming.getScore());
        p.prePersist();
        return progressRepo.save(p);
    }

    public List<Progress> getUserProgress(Long userId) {
        return progressRepo.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}