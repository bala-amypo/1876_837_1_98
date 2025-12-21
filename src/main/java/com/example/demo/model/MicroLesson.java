@Entity
@Table(name = "micro_lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer durationMinutes;
    private String contentType; // VIDEO, TEXT
    private String difficulty;  // BEGINNER, INTERMEDIATE, ADVANCED
    private String tags;
    private LocalDate publishDate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({"microLessons"})
    private Course course;

    @OneToMany(mappedBy = "microLesson", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"microLesson"})
    private List<Progress> progressList;
}
