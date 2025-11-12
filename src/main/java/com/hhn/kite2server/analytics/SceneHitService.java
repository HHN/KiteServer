package com.hhn.kite2server.analytics;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SceneHitService {

    private final SceneHitRepository repo;

    public SceneHitService(SceneHitRepository repo) {
        this.repo = repo;
    }

    // WRITE: mit Sperre
    @Transactional
    public long increment(SceneType scene) {
        try {
            SceneHit hit = repo.findBySceneForUpdate(scene).orElseGet(() -> {
                SceneHit n = new SceneHit();
                n.setScene(scene);
                n.setCount(0);
                return repo.saveAndFlush(n);
            });
            hit.setCount(hit.getCount() + 1);
            return repo.save(hit).getCount();
        } catch (DataIntegrityViolationException ex) {
            // zeitgleich von anderem Thread angelegt -> erneut laden & inkrementieren
            SceneHit hit = repo.findBySceneForUpdate(scene).orElseThrow();
            hit.setCount(hit.getCount() + 1);
            return repo.save(hit).getCount();
        }
    }


    // READS: read-only, ohne Sperren
    @Transactional(readOnly = true)
    public long getCount(SceneType scene) {
        return repo.findByScene(scene).map(SceneHit::getCount).orElse(0L);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getAllCounts() {
        Map<String, Long> map = new LinkedHashMap<>();
        for (SceneType t : SceneType.values()) {
            map.put(t.toValue(), getCount(t));
        }
        return map;
    }

    @Transactional
    public void resetAll() {
        repo.deleteAllInBatch();
    }
}
