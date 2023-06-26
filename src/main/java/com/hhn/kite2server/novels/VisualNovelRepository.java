package com.hhn.kite2server.novels;

import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VisualNovelRepository extends JpaRepository<VisualNovel, Long> {

    Optional<VisualNovel> findById(Long id);
}
