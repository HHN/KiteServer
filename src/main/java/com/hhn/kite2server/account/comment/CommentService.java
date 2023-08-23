package com.hhn.kite2server.account.comment;

import com.hhn.kite2server.account.comment.requests.ChangeCommentRequest;
import com.hhn.kite2server.account.comment.requests.DeleteCommentRequest;
import com.hhn.kite2server.account.comment.requests.GetCommentsRequest;
import com.hhn.kite2server.account.comment.requests.PostCommentRequest;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.novels.VisualNovelRepository;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VisualNovelRepository novelRepository;

    public ResultCode postComment(AppUser user, PostCommentRequest request) {
        if (user == null || request == null) {
            return ResultCode.FAILED_TO_POST_COMMENT;
        }
        Optional<VisualNovel> optionalNovel = novelRepository.findById(request.getVisualNovelId());

        if (!optionalNovel.isPresent()) {
            return ResultCode.NOVEL_NOT_FOUND;
        }
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setVisualNovel(optionalNovel.get());
        comment.setAuthor(user);
        commentRepository.save(comment);
        return ResultCode.SUCCESSFULLY_POSTED_COMMENT;
    }

    public Response getComments(GetCommentsRequest request) {
        Response response = new Response();

        if (request == null) {
            ResultCode code = ResultCode.FAILED_TO_GET_COMMENTS_FOR_NOVEL;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<VisualNovel> optionalNovel = novelRepository.findById(request.getVisualNovelId());

        if (!optionalNovel.isPresent()) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        List<Comment> commentList = commentRepository.findByVisualNovel(optionalNovel.get());
        response.setComments(commentList);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_COMMENTS_FOR_NOVEL;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public ResultCode changeComment(AppUser user, ChangeCommentRequest request) {
        if (user == null || request == null) {
            return ResultCode.FAILED_TO_UPDATE_COMMENT;
        }
        Optional<Comment> optionalComment = commentRepository.findById(request.getId());

        if (!optionalComment.isPresent()) {
            return ResultCode.COMMENT_NOT_FOUND;
        }
        if (!optionalComment.get().getAuthor().equals(user)) {
            return ResultCode.NOT_AUTHORIZED;
        }
        Comment comment = optionalComment.get();
        comment.setComment(request.getComment());
        commentRepository.save(comment);
        return ResultCode.SUCCESSFULLY_UPDATED_COMMENT;
    }

    public ResultCode deleteComment(AppUser user, DeleteCommentRequest request) {
        if (user == null || request == null) {
            return ResultCode.FAILED_TO_DELETE_COMMENT;
        }
        Optional<Comment> optionalComment = commentRepository.findById(request.getId());

        if (!optionalComment.isPresent()) {
            return ResultCode.COMMENT_NOT_FOUND;
        }
        if (!optionalComment.get().getAuthor().equals(user)) {
            return ResultCode.NOT_AUTHORIZED;
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return ResultCode.SUCCESSFULLY_DELETED_COMMENT;
    }

    public void deleteAllCommentsOfUser(AppUser user) {
        commentRepository.deleteByAuthor(user);
    }

    public void deleteAllCommentsOfVisualNovel(VisualNovel visualNovel) {
        commentRepository.deleteByVisualNovel(visualNovel);
    }
}
