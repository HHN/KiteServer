package com.hhn.kite2server.commentlikes;

import com.hhn.kite2server.comment.Comment;
import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findById(Long id);

    @Transactional
    void deleteByUser(AppUser user);

    @Transactional
    void deleteByComment(Comment comment);

    List<CommentLike> findByComment(Comment comment);

    Optional<CommentLike> findByUserAndComment(AppUser user, Comment comment);
}
