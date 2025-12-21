package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
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
    public Progress recordProgress(Long userId, Long lessonId, Progress updatedProgress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        Progress progress = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(Progress.builder()
                        .user(user)
                        .microLesson(lesson)
                        .build());

        progress.setStatus(updatedProgress.getStatus());
        progress.setProgressPercent(updatedProgress.getProgressPercent());
        progress.setScore(updatedProgress.getScore());

        return progressRepository.save(progress);
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
