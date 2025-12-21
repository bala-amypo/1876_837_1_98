package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "micro_lesson")
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "content_type")
    private String contentType;

    private String difficulty;

    private String tags;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTags() {
        return tags;
    }
 
    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }
 
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Course getCourse() {
        return course;
    }

    // ✅ CRITICAL — fixes course_id cannot be null
    public void setCourse(Course course) {
        this.course = course;
    }
}
