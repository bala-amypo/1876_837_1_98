// Example: LessonService.java
package com.example.demo.service;

import com.example.demo.entity.MicroLesson;
import java.util.List;

public interface LessonService {
    List<MicroLesson> getAllLessons();
    MicroLesson getLessonById(Long id);
    MicroLesson createLesson(MicroLesson lesson);
    void deleteLesson(Long id);
}
