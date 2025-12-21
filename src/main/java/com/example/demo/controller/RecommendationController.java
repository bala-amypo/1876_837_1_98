package com.example.demo.controller;

import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    // ✅ POST – generate recommendation
    @PostMapping("/generate")
    public List<String> generate() {
        return service.generate();
    }

    // ✅ GET – latest recommendation
    @GetMapping("/latest")
    public List<String> latest() {
        return service.generate();
    }
}
