package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository,
                             MicroLessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Course createCourse(Course course, Long instructorId) {
        // Check instructor exists
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        if (!"INSTRUCTOR".equalsIgnoreCase(instructor.getRole()) &&
            !"ADMIN".equalsIgnoreCase(instructor.getRole())) {
            throw new ValidationException("User is not authorized to create courses");
        }

        course.setInstructor(instructor);

        // First save course to generate ID
        Course created = courseRepository.save(course);

        // Save lessons if any
        if (course.getLessons() != null && !course.getLessons().isEmpty()) {
            for (MicroLesson lesson : course.getLessons()) {
                lesson.setCourse(created); // set course reference

                // Defaults
                if (lesson.getContentType() == null) lesson.setContentType("VIDEO");
                if (lesson.getDifficulty() == null) lesson.setDifficulty("BEGINNER");
                if (lesson.getPublishDate() == null) lesson.setPublishDate(LocalDate.now());

                if (lesson.getTitle() == null || lesson.getTitle().trim().isEmpty())
                    throw new ValidationException("Lesson title cannot be empty");
                if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15)
                    throw new ValidationException("Lesson duration must be 1-15 minutes");
            }
            lessonRepository.saveAll(course.getLessons());
        }

        return created;
    }

    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}
