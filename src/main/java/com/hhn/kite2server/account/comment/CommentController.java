package com.hhn.kite2server.account.comment;

import com.hhn.kite2server.account.comment.requests.ChangeCommentRequest;
import com.hhn.kite2server.account.comment.requests.DeleteCommentRequest;
import com.hhn.kite2server.account.comment.requests.GetCommentsRequest;
import com.hhn.kite2server.account.comment.requests.PostCommentRequest;
import com.hhn.kite2server.account.commentlikes.CommentLikeService;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"comment"})
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Response postComment(@AuthenticationPrincipal AppUser user, @RequestBody PostCommentRequest request) {
        return commentService.postComment(user, request);
    }

    @GetMapping
    public Response getComments(@AuthenticationPrincipal AppUser user, @RequestBody GetCommentsRequest request) {
        return commentService.getComments(user, request);
    }

    @PutMapping
    public Response changeComment(@AuthenticationPrincipal AppUser user, @RequestBody ChangeCommentRequest request) {
        return commentService.changeComment(user, request);
    }

    @DeleteMapping
    public Response deleteComment(@AuthenticationPrincipal AppUser user, @RequestBody DeleteCommentRequest request) {
        return commentService.deleteComment(user, request);
    }
}
