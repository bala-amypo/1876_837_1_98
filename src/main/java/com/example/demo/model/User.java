// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.time.LocalDateTime;
// import java.util.List;

// @Entity
// @Table(name = "users")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String fullName;

//     @Column(nullable = false, unique = true)
//     private String email;

//     @Column(nullable = false)
//     private String password;

//     @Column(nullable = false)
//     private String role; // ADMIN, INSTRUCTOR, STUDENT

//     private String preferredLearningStyle;

//     @Column(nullable = false, updatable = false)
//     private LocalDateTime createdAt;

//     // ✅ AUTO SET createdAt
//     @PrePersist
//     protected void onCreate() {
//         this.createdAt = LocalDateTime.now();
//     }

//     @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
//     private List<Course> courses;

//     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//     private List<Progress> progresses;

//     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//     private List<Recommendation> recommendations;
// }
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    // ❌ Never expose password
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ADMIN, INSTRUCTOR, STUDENT

    private String preferredLearningStyle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ✅ AUTO SET createdAt
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ❌ Prevent recursion in Course → User → Course
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courses;

    // ❌ Prevent recursion
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Progress> progresses;

    // ❌ Prevent recursion
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Recommendation> recommendations;
}
