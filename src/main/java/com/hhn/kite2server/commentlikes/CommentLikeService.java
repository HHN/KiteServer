package com.hhn.kite2server.commentlikes;

import com.hhn.kite2server.comment.Comment;
import com.hhn.kite2server.comment.CommentService;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    public Response likeComment(AppUser user, LikeCommentRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_LIKE_COMMENT;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<Comment> optionalComment = commentService.findById(request.getId());

        if (!optionalComment.isPresent()) {
            ResultCode code = ResultCode.COMMENT_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Comment comment = optionalComment.get();
        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLikeRepository.save(commentLike);

        ResultCode code = ResultCode.SUCCESSFULLY_LIKED_COMMENT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        commentService.SetComments(user, comment.getVisualNovelId(), response);
        return response;
    }

    public Response unlikeComment(AppUser user, UnlikeCommentRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_UNLIKE_COMMENT;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<Comment> optionalComment = commentService.findById(request.getId());

        if (!optionalComment.isPresent()) {
            ResultCode code = ResultCode.COMMENT_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Comment comment = optionalComment.get();
        Optional<CommentLike> optionalCommentLike = commentLikeRepository.findByUserAndComment(user, comment);

        if (!optionalCommentLike.isPresent()) {
            ResultCode code = ResultCode.COMMENT_LIKE_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        commentLikeRepository.delete(optionalCommentLike.get());
        ResultCode code = ResultCode.SUCCESSFULLY_UNLIKED_COMMENT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        commentService.SetComments(user, comment.getVisualNovelId(), response);
        return response;
    }

    public void deleteLikesByUser(AppUser user) {
        commentLikeRepository.deleteByUser(user);
    }

    public void deleteLikesByComment(Comment comment) {
        commentLikeRepository.deleteByComment(comment);
    }
}
