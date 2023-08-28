package com.hhn.kite2server.account.commentlikes;

import com.hhn.kite2server.account.comment.Comment;
import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findById(Long id);

    void deleteByUser(AppUser user);

    void deleteByComment(Comment comment);

    List<CommentLike> findByComment(Comment comment);

    Optional<CommentLike> findByUserAndComment(AppUser user, Comment comment);
}
