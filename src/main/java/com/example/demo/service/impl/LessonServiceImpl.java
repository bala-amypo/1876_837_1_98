package com.example.demo.service.impl;

import com.example.demo.service.LessonService;
import com.example.demo.entity.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final MicroLessonRepository microLessonRepository;

    @Override
    public List<MicroLesson> getAllLessons() {
        return microLessonRepository.findAll();
    }

    @Override
    public MicroLesson getLessonById(Long id) {
        return microLessonRepository.findById(id).orElseThrow();
    }

    @Override
    public MicroLesson createLesson(MicroLesson lesson) {
        return microLessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        microLessonRepository.deleteById(id);
    }
}
