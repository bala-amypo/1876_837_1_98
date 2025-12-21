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
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        if (!"INSTRUCTOR".equalsIgnoreCase(instructor.getRole()) &&
            !"ADMIN".equalsIgnoreCase(instructor.getRole())) {
            throw new ValidationException("User is not authorized to create courses");
        }

        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
            throw new ValidationException("Course title already exists for this instructor");
        }

        course.setInstructor(instructor);
        Course savedCourse = courseRepository.save(course); // Save first to get ID

        if (course.getLessons() != null && !course.getLessons().isEmpty()) {
            for (MicroLesson lesson : course.getLessons()) {
                lesson.setCourse(savedCourse);

                if (lesson.getContentType() == null) lesson.setContentType("VIDEO");
                if (lesson.getDifficulty() == null) lesson.setDifficulty("BEGINNER");
                if (lesson.getPublishDate() == null) lesson.setPublishDate(LocalDate.now());
            }
            lessonRepository.saveAll(course.getLessons());
        }

        return savedCourse;
    }

    @Override
    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setCategory(updatedCourse.getCategory());

        return courseRepository.save(course);
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
