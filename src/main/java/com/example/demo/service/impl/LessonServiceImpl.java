package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }
}
