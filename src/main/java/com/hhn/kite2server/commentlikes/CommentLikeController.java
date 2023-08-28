package com.hhn.kite2server.commentlikes;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"commentlike"})
@AllArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public Response likeComment(@AuthenticationPrincipal AppUser user, @RequestBody LikeCommentRequest request) {
        return commentLikeService.likeComment(user, request);
    }

    @DeleteMapping
    public Response unlikeComment(@AuthenticationPrincipal AppUser user, @RequestBody UnlikeCommentRequest request) {
        return commentLikeService.unlikeComment(user, request);
    }
}
