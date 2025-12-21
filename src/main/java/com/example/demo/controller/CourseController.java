@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course createCourse(
            @RequestParam Long instructorId,
            @RequestBody Course course
    ) {
        return courseService.createCourse(course, instructorId);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<Course> getCoursesByInstructor(@PathVariable Long instructorId) {
        return courseService.listCoursesByInstructor(instructorId);
    }
}
