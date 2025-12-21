package com.example.demo.service.impl;

import com.example.demo.model.Progress;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository repository;

    public ProgressServiceImpl(ProgressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Progress saveProgress(Long lessonId, Progress progress) {
        progress.setLessonId(lessonId);
        return repository.save(progress);
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Progress getLessonProgress(Long lessonId) {
        return repository.findByLessonId(lessonId);
    }
}
