package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course, User instructor) {
        if (!"INSTRUCTOR".equals(instructor.getRole())) {
            throw new RuntimeException("Only instructors can create courses");
        }
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }
}
