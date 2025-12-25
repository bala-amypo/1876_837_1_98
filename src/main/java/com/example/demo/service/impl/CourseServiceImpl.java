package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("No user"));
        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) throw new RuntimeException("Title exists");
        course.setInstructor(instructor);
        course.prePersist();
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course update) {
        Course existing = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setTitle(update.getTitle());
        existing.setDescription(update.getDescription());
        return courseRepository.save(existing);
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
}