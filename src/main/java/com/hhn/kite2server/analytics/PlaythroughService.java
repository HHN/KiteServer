package com.hhn.kite2server.analytics;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaythroughService {

    private static final String SLUG = "PLAYTHROUGHS";

    private final PlaythroughRepository repo;

    public PlaythroughService(PlaythroughRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public long increment() {
        try {
            PlaythroughCounter pc = repo.findBySlugForUpdate(SLUG).orElseGet(() -> {
                PlaythroughCounter n = new PlaythroughCounter();
                n.setSlug(SLUG);
                n.setCount(0);
                return repo.saveAndFlush(n);
            });
            pc.setCount(pc.getCount() + 1);
            return repo.save(pc).getCount();
        } catch (DataIntegrityViolationException e) {
            // Rennen beim ersten Insert -> erneut laden & erhöhen
            PlaythroughCounter pc = repo.findBySlugForUpdate(SLUG).orElseThrow();
            pc.setCount(pc.getCount() + 1);
            return repo.save(pc).getCount();
        }
    }

    @Transactional(readOnly = true)
    public long getTotal() {
        return repo.findBySlug(SLUG).map(PlaythroughCounter::getCount).orElse(0L);
    }

    @Transactional
    public void reset() {
        repo.deleteAllInBatch();
    }
}
