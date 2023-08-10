package com.hhn.kite2server.novels;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VisualNovelRepository extends JpaRepository<VisualNovel, Long> {

    Optional<VisualNovel> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = """
    delete from VisualNovel t where t.creator = :user
    """)
    void deleteNovelsFromUser(@Param("user") AppUser user);
}
