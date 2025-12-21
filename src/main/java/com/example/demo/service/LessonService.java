package com.example.demo.service;

import com.example.demo.model.MicroLesson;
import java.util.List;

public interface LessonService {
    MicroLesson saveLesson(MicroLesson lesson);
    List<MicroLesson> getAllLessons();
    MicroLesson getLessonById(Long id);
    void deleteLesson(Long id);
}
