package com.hhn.kite2server.novellikes;

import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NovelLikeRepository extends JpaRepository<NovelLike, Long> {

    Optional<NovelLike> findById(Long id);

    @Transactional
    void deleteByUser(AppUser user);

    @Transactional
    void deleteByVisualNovelId(Long visualNovelId);

    List<NovelLike> findByVisualNovelId(Long visualNovelId);

    Optional<NovelLike> findByUserAndVisualNovelId(AppUser user, Long visualNovelId);
}
