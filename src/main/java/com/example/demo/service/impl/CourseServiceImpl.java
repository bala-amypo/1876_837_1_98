package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(Long instructorId, Course course) {

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!"INSTRUCTOR".equalsIgnoreCase(instructor.getRole())) {
            throw new RuntimeException("User not authorized");
        }

        course.setInstructor(instructor);

        // 🔥 SAVE COURSE FIRST
        Course savedCourse = courseRepository.save(course);

        // 🔥 LINK LESSONS TO COURSE
        if (course.getLessons() != null) {
            for (MicroLesson lesson : course.getLessons()) {
                lesson.setCourse(savedCourse);
            }
        }

        return savedCourse;
    }
}
