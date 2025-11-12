// src/main/java/com/hhn/kite2server/analytics/MetricsAdminController.java
package com.hhn.kite2server.analytics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metrics")
public class MetricsAdminController {

    private final SceneHitService sceneHitService;
    private final PlaythroughService playthroughService;

    public MetricsAdminController(SceneHitService sceneHitService, PlaythroughService playthroughService) {
        this.sceneHitService = sceneHitService;
        this.playthroughService = playthroughService;
    }

    // Shell (mit Basic Auth) ruft: POST /metrics/reset-all
    @PostMapping("/reset-all")
    public ResponseEntity<Map<String, Object>> resetAll() {
        sceneHitService.resetAll();
        playthroughService.reset();
        return ResponseEntity.ok(Map.of(
                "resetScenes", true,
                "resetPlaythroughs", true
        ));
    }
}
