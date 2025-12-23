// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// @Entity
// @Table(name = "course")
// public class Course {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String title;
//     private String description;
//     private String category;

//     private LocalDate createdAt = LocalDate.now();

//     @ManyToOne
//     @JoinColumn(name = "instructor_id", nullable = false)
//     private User instructor;

//     @OneToMany(
//         mappedBy = "course",
//         cascade = CascadeType.ALL,
//         orphanRemoval = true
//     )
//     private List<MicroLesson> lessons = new ArrayList<>();

//     // ---------- GETTERS & SETTERS ----------

//     public Long getId() {
//         return id;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getDescription() {
//         return description;
//     }
 
//     public void setDescription(String description) {
//         this.description = description;
//     }
 
//     public String getCategory() {
//         return category;
//     }
 
//     public void setCategory(String category) {
//         this.category = category;
//     }
 
//     public LocalDate getCreatedAt() {
//         return createdAt;
//     }
 
//     public User getInstructor() {
//         return instructor;
//     }
 
//     public void setInstructor(User instructor) {
//         this.instructor = instructor;
//     }

//     public List<MicroLesson> getLessons() {
//         return lessons;
//     }

//     public void setLessons(List<MicroLesson> lessons) {
//         this.lessons = lessons;
//     }
// }
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @NotBlank
    @Size(max = 50)
    private String category;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}