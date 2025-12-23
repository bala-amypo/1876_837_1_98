// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.exception.ValidationException;
// import com.example.demo.model.Course;
// import com.example.demo.model.MicroLesson;
// import com.example.demo.repository.CourseRepository;
// import com.example.demo.repository.MicroLessonRepository;
// import com.example.demo.service.LessonService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDate;
// import java.util.List;

// @Service
// @Transactional
// public class LessonServiceImpl implements LessonService {

//     private final MicroLessonRepository lessonRepository;
//     private final CourseRepository courseRepository;

//     public LessonServiceImpl(MicroLessonRepository lessonRepository,
//                              CourseRepository courseRepository) {
//         this.lessonRepository = lessonRepository;
//         this.courseRepository = courseRepository;
//     }

//     @Override
//     public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
//         Course course = courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

//         if (lesson.getDurationMinutes() == null ||
//                 lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
//             throw new ValidationException("Lesson duration must be between 1 and 15 minutes");
//         }

//         if (lesson.getTitle() == null || lesson.getTitle().trim().isEmpty()) {
//             throw new ValidationException("Lesson title cannot be null or empty");
//         }

//         if (lesson.getContentType() == null || lesson.getContentType().trim().isEmpty()) {
//             lesson.setContentType("VIDEO");
//         }
//         if (lesson.getDifficulty() == null || lesson.getDifficulty().trim().isEmpty()) {
//             lesson.setDifficulty("BEGINNER");
//         }
//         if (lesson.getTags() == null) lesson.setTags("");
//         if (lesson.getPublishDate() == null) lesson.setPublishDate(LocalDate.now());

//         lesson.setCourse(course);
//         return lessonRepository.save(lesson);
//     }

//     @Override
//     public MicroLesson updateLesson(Long lessonId, MicroLesson updatedLesson) {
//         MicroLesson lesson = lessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

//         lesson.setTitle(updatedLesson.getTitle());
//         lesson.setDurationMinutes(updatedLesson.getDurationMinutes());
//         lesson.setContentType(updatedLesson.getContentType());
//         lesson.setDifficulty(updatedLesson.getDifficulty());
//         lesson.setTags(updatedLesson.getTags());

//         return lessonRepository.save(lesson);
//     }

//     // ✅ method renamed to match interface
//     @Override
//     public List<MicroLesson> search(String tags, String difficulty, String contentType) {
//         return lessonRepository.findByTagsContainingAndDifficultyAndContentType(tags, difficulty, contentType);
//     }

//     @Override
//     public MicroLesson getLesson(Long lessonId) {
//         return lessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
//     }
// // }
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository microLessonRepository, CourseRepository courseRepository) {
        this.microLessonRepository = microLessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
        }
        
        if (lesson.getPublishDate() == null) {
            lesson.setPublishDate(java.time.LocalDate.now());
        }
        
        lesson.setCourse(course);
        return microLessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existingLesson = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        
        if (lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
        }
        
        existingLesson.setTitle(lesson.getTitle());
        existingLesson.setDurationMinutes(lesson.getDurationMinutes());
        existingLesson.setContentType(lesson.getContentType());
        existingLesson.setDifficulty(lesson.getDifficulty());
        existingLesson.setTags(lesson.getTags());
        existingLesson.setPublishDate(lesson.getPublishDate());
        
        return microLessonRepository.save(existingLesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}