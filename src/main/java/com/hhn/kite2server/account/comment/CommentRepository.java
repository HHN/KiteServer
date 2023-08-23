package com.hhn.kite2server.account.comment;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.novels.VisualNovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    void deleteByAuthor(AppUser appUser);

    void deleteByVisualNovel(VisualNovel visualNovel);

    List<Comment> findByVisualNovel(VisualNovel visualNovel);
}
