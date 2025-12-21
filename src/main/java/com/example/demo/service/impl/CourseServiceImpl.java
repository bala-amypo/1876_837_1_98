package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

@Service   // 🔥 THIS WAS MISSING OR WRONG
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            UserRepository userRepository,
            MicroLessonRepository microLessonRepository) {

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
    }

    @Override
    public Course createCourse(Course course, Long instructorId) {

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!"INSTRUCTOR".equals(instructor.getRole())) {
            throw new RuntimeException("User is not an instructor");
        }

        course.setInstructor(instructor);
        Course savedCourse = courseRepository.save(course);

        if (course.getLessons() != null) {
            for (MicroLesson lesson : course.getLessons()) {
                lesson.setCourse(savedCourse);
                microLessonRepository.save(lesson);
            }
        }

        return savedCourse;
    }
}
