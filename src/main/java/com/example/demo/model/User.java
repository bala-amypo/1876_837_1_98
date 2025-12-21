@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // LEARNER, INSTRUCTOR, ADMIN
    private String preferredLearningStyle;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"instructor"})
    private List<Course> courses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Progress> progresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Recommendation> recommendations;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
