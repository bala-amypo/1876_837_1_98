package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Lesson duration must be greater than 0");
        }
        if (lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Lesson duration cannot exceed 15 minutes");
        }

        lesson.setCourse(course);
        return microLessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson updated) {
        MicroLesson existing = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        existing.setTitle(updated.getTitle());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setContentType(updated.getContentType());
        existing.setDifficulty(updated.getDifficulty());
        existing.setTags(updated.getTags());
        existing.setPublishDate(updated.getPublishDate());

        return microLessonRepository.save(existing);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByTagsContainingAndDifficultyAndContentType(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
    }
}
