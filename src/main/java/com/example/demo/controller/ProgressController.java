@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @PostMapping("/{lessonId}")
    public Progress save(
            @PathVariable Long lessonId,
            @RequestBody Progress progress) {
        return service.saveProgress(lessonId, progress);
    }

    @GetMapping("/user/{userId}")
    public List<Progress> userProgress(@PathVariable Long userId) {
        return service.getUserProgress(userId);
    }

    @GetMapping("/lesson/{lessonId}")
    public Progress lessonProgress(@PathVariable Long lessonId) {
        return service.getLessonProgress(lessonId);
    }
}
