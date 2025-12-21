package com.example.demo.service;

import com.example.demo.model.Progress;
import com.example.demo.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository repository;

    public ProgressService(ProgressRepository repository) {
        this.repository = repository;
    }

    public List<Progress> getAllProgress() {
        return repository.findAll();
    }

    public Progress getProgressById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found with id: " + id));
    }

    public Progress createProgress(Progress progress) {
        return repository.save(progress);
    }

    public Progress updateProgress(Long id, Progress updatedProgress) {
        Progress existing = getProgressById(id);
        existing.setStatus(updatedProgress.getStatus());
        existing.setProgressPercent(updatedProgress.getProgressPercent());
        existing.setScore(updatedProgress.getScore());
        existing.setUser(updatedProgress.getUser());
        existing.setMicroLesson(updatedProgress.getMicroLesson());
        return repository.save(existing);
    }

    public void deleteProgress(Long id) {
        repository.deleteById(id);
    }
}
