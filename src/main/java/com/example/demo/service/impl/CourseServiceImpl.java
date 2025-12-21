package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        if (!("INSTRUCTOR".equalsIgnoreCase(instructor.getRole()) ||
              "ADMIN".equalsIgnoreCase(instructor.getRole()))) {
            throw new IllegalArgumentException("Only INSTRUCTOR or ADMIN can create courses");
        }

        if (courseRepository.existsByTitleAndInstructorId(course.getTitle(), instructorId)) {
            throw new IllegalArgumentException("Course title already exists for this instructor");
        }

        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long courseId, Course newCourse) {
        Course existing = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        existing.setTitle(newCourse.getTitle());
        existing.setDescription(newCourse.getDescription());
        existing.setCategory(newCourse.getCategory());

        return courseRepository.save(existing);
    }

    @Override
    public List<Course> listCoursesByInstructor(Long instructorId) {
        return courseRepository.findAll()
                .stream()
                .filter(c -> c.getInstructor() != null &&
                             instructorId.equals(c.getInstructor().getId()))
                .toList();
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }
}
