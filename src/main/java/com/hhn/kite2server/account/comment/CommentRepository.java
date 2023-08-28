package com.hhn.kite2server.account.comment;

import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    void deleteByAuthor(AppUser appUser);

    void deleteByVisualNovelId(Long visualNovelId);

    List<Comment> findByVisualNovelId(Long visualNovelId);
}
