package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
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

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Invalid lesson duration");
        }

        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson updatedLesson) {
        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        lesson.setTitle(updatedLesson.getTitle());
        lesson.setDurationMinutes(updatedLesson.getDurationMinutes());
        lesson.setContentType(updatedLesson.getContentType());
        lesson.setDifficulty(updatedLesson.getDifficulty());
        lesson.setTags(updatedLesson.getTags());
        lesson.setPublishDate(updatedLesson.getPublishDate());

        return lessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return lessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}
