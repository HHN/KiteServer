package com.hhn.kite2server.analytics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metrics/playthroughs")
public class PlaythroughController {

    private final PlaythroughService service;

    public PlaythroughController(PlaythroughService service) {
        this.service = service;
    }

    // Unity ruft das auf, wenn ein Durchlauf beendet ist
    @PostMapping("/hit")
    public ResponseEntity<Map<String, Object>> hit() {
        long total = service.increment();
        return ResponseEntity.ok(Map.of("metric", "playthroughs", "count", total));
    }

    // Admin/Shell (Basic Auth in Security konfigurieren)
    @GetMapping
    public ResponseEntity<Map<String, Long>> get() {
        return ResponseEntity.ok(Map.of("playthroughs", service.getTotal()));
    }
}
