package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final List<Course> courses = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Course createCourse(Course course, Long instructorId) {
        course.setId(nextId++);
        course.setInstructorId(instructorId); // set instructorId
        courses.add(course);
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<Course> courseOpt = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return courseOpt.orElse(null);
    }

    @Override
    public void deleteCourse(Long id) {
        courses.removeIf(c -> c.getId().equals(id));
    }
}
