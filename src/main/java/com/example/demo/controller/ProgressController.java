package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @GetMapping
    public List<Progress> getAllProgress() {
        return service.getAllProgress();
    }

    @GetMapping("/{id}")
    public Progress getProgress(@PathVariable Long id) {
        return service.getProgressById(id);
    }

    @PostMapping
    public Progress createProgress(@RequestBody Progress progress) {
        return service.createProgress(progress);
    }

    @PutMapping("/{id}")
    public Progress updateProgress(@PathVariable Long id, @RequestBody Progress progress) {
        return service.updateProgress(id, progress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        service.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
