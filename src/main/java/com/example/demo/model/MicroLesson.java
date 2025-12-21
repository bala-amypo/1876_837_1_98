package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contentType;
    private String difficulty;
    private Integer durationMinutes;
    private String tags;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    // getters/setters
    public Long getId() { return id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
