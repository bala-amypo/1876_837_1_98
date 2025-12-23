// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.time.LocalDate;
// import java.util.List;

// @Entity
// @Table(name = "micro_lessons")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class MicroLesson {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String title;
//     private Integer durationMinutes;
//     private String contentType;
//     private String difficulty;
//     private String tags;
//     private LocalDate publishDate;

//     @ManyToOne
//     @JoinColumn(name = "course_id", nullable = false)
//     private Course course;

//     @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL)
//     private List<Progress> progressList;
// // }
// package com.example.demo.model;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Positive;
// import jakarta.validation.constraints.Size;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.time.LocalDate;

// @Entity
// @Table(name = "micro_lessons")
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class MicroLesson {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "course_id", nullable = false)
//     private Course course;

//     @NotBlank
//     @Size(max = 150)
//     private String title;

//     @NotNull
//     @Positive
//     private Integer durationMinutes;

//     @NotBlank
//     @Size(max = 50)
//     private String contentType;

//     @NotBlank
//     @Size(max = 50)
//     private String difficulty;

//     @Size(max = 500)
//     private String tags;

//     @NotNull
//     private LocalDate publishDate;
// }
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "micro_lessons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MicroLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({"instructor"})
    private Course course;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotBlank
    @Size(max = 50)
    private String contentType;

    @NotBlank
    @Size(max = 50)
    private String difficulty;

    @Size(max = 500)
    private String tags;

    @NotNull
    private LocalDate publishDate;
}