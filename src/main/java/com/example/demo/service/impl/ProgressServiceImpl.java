package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final MicroLessonRepository microLessonRepository;
    private final UserRepository userRepository;

    @Override
    public Progress startLesson(Long userId, Long lessonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        MicroLesson lesson = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        Progress progress = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(Progress.builder()
                        .user(user)
                        .microLesson(lesson)
                        .progressPercent(0.0)
                        .startedAt(LocalDateTime.now())
                        .build());

        progress.setLastAccessedAt(LocalDateTime.now());
        return progressRepository.save(progress);
    }

    @Override
    public Progress completeLesson(Long userId, Long lessonId) {
        Progress progress = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Progress record not found"));

        progress.setProgressPercent(100.0);
        progress.setCompletedAt(LocalDateTime.now());
        return progressRepository.save(progress);
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
