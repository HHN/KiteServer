package com.hhn.kite2server.analytics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metrics/scenes")
public class SceneHitController {

    private final SceneHitService service;

    public SceneHitController(SceneHitService service) {
        this.service = service;
    }

    @PostMapping("/{scene}/hit")
    public ResponseEntity<Map<String, Object>> hit(@PathVariable("scene") String scene) {
        SceneType type = SceneType.fromValue(scene);
        long total = service.increment(type);
        return ResponseEntity.ok(Map.of("scene", type.toValue(), "count", total));
    }

    @GetMapping
    public ResponseEntity<Map<String, Long>> getAll() {
        return ResponseEntity.ok(service.getAllCounts());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badScene(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}

