package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;

    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        if (progress.getProgressPercent() < 0 || progress.getProgressPercent() > 100)
            throw new IllegalArgumentException("Progress percent must be between 0 and 100");

        if ("COMPLETED".equals(progress.getStatus()) && progress.getProgressPercent() != 100)
            throw new IllegalArgumentException("Completed lessons must have 100% progress");

        Progress existing = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId).orElse(null);
        if (existing != null) {
            existing.setProgressPercent(progress.getProgressPercent());
            existing.setStatus(progress.getStatus());
            existing.setScore(progress.getScore());
            return progressRepository.save(existing);
        } else {
            progress.setUser(user);
            progress.setMicroLesson(lesson);
            return progressRepository.save(progress);
        }
    }

    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}
