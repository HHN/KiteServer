package com.hhn.kite2server.score;

import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByUser(AppUser user);

    Optional<Score> findById(Long id);

    @Transactional
    Optional<Score> deleteByUser(AppUser user);
}
