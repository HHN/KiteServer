package com.hhn.kite2server.analytics;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaythroughRepository extends JpaRepository<PlaythroughCounter, Long> {

    // READ (ohne Sperre)
    Optional<PlaythroughCounter> findBySlug(String slug);

    // WRITE (mit Sperre)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PlaythroughCounter p where p.slug = :slug")
    Optional<PlaythroughCounter> findBySlugForUpdate(@Param("slug") String slug);
}
