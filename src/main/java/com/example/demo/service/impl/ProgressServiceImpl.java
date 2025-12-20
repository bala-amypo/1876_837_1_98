package com.example.demo.service.impl;

import com.example.demo.model.Progress;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }
}
