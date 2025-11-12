package com.hhn.kite2server.analytics;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SceneHitRepository extends JpaRepository<SceneHit, Long> {

    // Für READS (keine Sperre)
    Optional<SceneHit> findByScene(SceneType scene);

    // Für increment() (mit Schreibsperre)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SceneHit s where s.scene = :scene")
    Optional<SceneHit> findBySceneForUpdate(@Param("scene") SceneType scene);
}
