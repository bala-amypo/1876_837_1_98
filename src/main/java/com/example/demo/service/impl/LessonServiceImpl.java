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

// import java.util.List;

// @Service
// @Transactional
// public class LessonServiceImpl implements LessonService {

//     private final MicroLessonRepository lessonRepository;
//     private final CourseRepository courseRepository;

//     public LessonServiceImpl(MicroLessonRepository lessonRepository, CourseRepository courseRepository) {
//         this.lessonRepository = lessonRepository;
//         this.courseRepository = courseRepository;
//     }

//     @Override
//     public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
//         Course course = courseRepository.findById(courseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

//         if (lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
//             throw new ValidationException("Lesson duration must be between 1 and 15 minutes");
//         }

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

//     @Override
//     public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
//         return lessonRepository.findByTagsContainingAndDifficultyAndContentType(tags, difficulty, contentType);
//     }

//     @Override
//     public MicroLesson getLesson(Long lessonId) {
//         return lessonRepository.findById(lessonId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Title
        if (lesson.getTitle() == null || lesson.getTitle().trim().isEmpty()) {
            throw new ValidationException("Lesson title cannot be null or empty");
        }

        // Duration
        if (lesson.getDurationMinutes() == null || lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new ValidationException("Lesson duration must be between 1 and 15 minutes");
        }

        // Content type
        if (lesson.getContentType() == null || lesson.getContentType().trim().isEmpty()) {
            lesson.setContentType("VIDEO"); // default
        } else {
            String type = lesson.getContentType().toUpperCase();
            if (!type.equals("VIDEO") && !type.equals("TEXT")) {
                throw new ValidationException("Content type must be VIDEO or TEXT");
            }
            lesson.setContentType(type);
        }

        // Difficulty
        if (lesson.getDifficulty() == null || lesson.getDifficulty().trim().isEmpty()) {
            lesson.setDifficulty("BEGINNER");
        } else {
            String diff = lesson.getDifficulty().toUpperCase();
            if (!diff.equals("BEGINNER") && !diff.equals("INTERMEDIATE") && !diff.equals("ADVANCED")) {
                throw new ValidationException("Difficulty must be BEGINNER, INTERMEDIATE, or ADVANCED");
            }
            lesson.setDifficulty(diff);
        }

        // Tags
        if (lesson.getTags() == null) lesson.setTags("");

        // Publish date
        if (lesson.getPublishDate() == null) lesson.setPublishDate(LocalDate.now());

        // Link course
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson updatedLesson) {
        MicroLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        // Title
        if (updatedLesson.getTitle() != null && !updatedLesson.getTitle().trim().isEmpty()) {
            lesson.setTitle(updatedLesson.getTitle());
        }

        // Duration
        if (updatedLesson.getDurationMinutes() != null) {
            if (updatedLesson.getDurationMinutes() <= 0 || updatedLesson.getDurationMinutes() > 15) {
                throw new ValidationException("Lesson duration must be between 1 and 15 minutes");
            }
            lesson.setDurationMinutes(updatedLesson.getDurationMinutes());
        }

        // Content type
        if (updatedLesson.getContentType() != null && !updatedLesson.getContentType().trim().isEmpty()) {
            String type = updatedLesson.getContentType().toUpperCase();
            if (!type.equals("VIDEO") && !type.equals("TEXT")) {
                throw new ValidationException("Content type must be VIDEO or TEXT");
            }
            lesson.setContentType(type);
        }

        // Difficulty
        if (updatedLesson.getDifficulty() != null && !updatedLesson.getDifficulty().trim().isEmpty()) {
            String diff = updatedLesson.getDifficulty().toUpperCase();
            if (!diff.equals("BEGINNER") && !diff.equals("INTERMEDIATE") && !diff.equals("ADVANCED")) {
                throw new ValidationException("Difficulty must be BEGINNER, INTERMEDIATE, or ADVANCED");
            }
            lesson.setDifficulty(diff);
        }

        // Tags
        if (updatedLesson.getTags() != null) lesson.setTags(updatedLesson.getTags());

        // Publish date
        if (updatedLesson.getPublishDate() != null) lesson.setPublishDate(updatedLesson.getPublishDate());

        return lessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return lessonRepository.findByTagsContainingAndDifficultyAndContentType(
                tags != null ? tags : "",
                difficulty != null ? difficulty.toUpperCase() : "BEGINNER",
                contentType != null ? contentType.toUpperCase() : "VIDEO"
        );
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}
