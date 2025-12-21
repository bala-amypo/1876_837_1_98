package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

// @Service
// public class LessonServiceImpl implements LessonService {

//     private final MicroLessonRepository lessonRepository;

//     public LessonServiceImpl(MicroLessonRepository lessonRepository) {
//         this.lessonRepository = lessonRepository;
//     }

//     @Override
//     public MicroLesson getLesson(Long lessonId) {
//         return lessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new RuntimeException("Lesson not found"));
//     }
// }
@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository repository;

    public LessonServiceImpl(MicroLessonRepository repository) {
        this.repository = repository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        return repository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        lesson.setId(lessonId);
        return repository.save(lesson);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return repository.findById(lessonId).orElseThrow();
    }

    @Override
    public List<MicroLesson> search(String difficulty, String contentType) {
        return repository.findAll();
    }
}

