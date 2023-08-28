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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VisualNovelRepository novelRepository;

    public Response postComment(AppUser user, PostCommentRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_POST_COMMENT;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        Optional<VisualNovel> optionalNovel = novelRepository.findById(request.getVisualNovelId());

        if (!optionalNovel.isPresent() && (request.getVisualNovelId() >= 0)) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setVisualNovelId(request.getVisualNovelId());
        comment.setAuthor(user);
        commentRepository.save(comment);
        ResultCode code = ResultCode.SUCCESSFULLY_POSTED_COMMENT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        SetComments(user.getUsername(), request.getVisualNovelId(), response);
        return response;
    }

    public Response getComments(AppUser user, GetCommentsRequest request) {
        Response response = new Response();

        if (request == null) {
            ResultCode code = ResultCode.FAILED_TO_GET_COMMENTS_FOR_NOVEL;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<VisualNovel> optionalNovel = novelRepository.findById(request.getVisualNovelId());

        if (!optionalNovel.isPresent() && (request.getVisualNovelId() >= 0)) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        String username = "";
        if (user != null) { username = user.getUsername(); }
        SetComments(username, request.getVisualNovelId(), response);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_COMMENTS_FOR_NOVEL;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    private void SetComments(String username, Long visualNovelId, Response response) {
        List<Comment> commentList = commentRepository.findByVisualNovelId(visualNovelId);
        List<CommentInformation> responseList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentInformation commentInformation = new CommentInformation();
            commentInformation.setComment(comment.getComment());
            commentInformation.setId(comment.getId());
            commentInformation.setAuthor(comment.getAuthor().getUsername());
            commentInformation.setIsOwnComment(username.equals(commentInformation.getAuthor()));
            //Set liked
            //Set like count
            responseList.add(commentInformation);
        }
        responseList.sort(Comparator.comparingLong(CommentInformation::getId));
        response.setComments(responseList);
    }

    public Response changeComment(AppUser user, ChangeCommentRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_UPDATE_COMMENT;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<Comment> optionalComment = commentRepository.findById(request.getId());

        if (!optionalComment.isPresent()) {
            ResultCode code = ResultCode.COMMENT_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        if (!optionalComment.get().getAuthor().equals(user)) {
            ResultCode code = ResultCode.NOT_AUTHORIZED;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Comment comment = optionalComment.get();
        comment.setComment(request.getComment());
        commentRepository.save(comment);
        ResultCode code = ResultCode.SUCCESSFULLY_UPDATED_COMMENT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        SetComments(user.getUsername(), comment.getVisualNovelId(), response);
        return response;
    }

    public Response deleteComment(AppUser user, DeleteCommentRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_DELETE_COMMENT;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<Comment> optionalComment = commentRepository.findById(request.getId());

        if (!optionalComment.isPresent()) {
            ResultCode code = ResultCode.COMMENT_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        if (!optionalComment.get().getAuthor().equals(user)) {
            ResultCode code = ResultCode.NOT_AUTHORIZED;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Comment comment = optionalComment.get();
        long visualNovelId = comment.getVisualNovelId();
        commentRepository.delete(comment);
        ResultCode code = ResultCode.SUCCESSFULLY_DELETED_COMMENT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        SetComments(user.getUsername(), visualNovelId, response);
        return response;
    }

    public void deleteAllCommentsOfUser(AppUser user) {
        commentRepository.deleteByAuthor(user);
    }

    public void deleteAllCommentsOfVisualNovel(Long visualNovelId) {
        commentRepository.deleteByVisualNovelId(visualNovelId);
    }
}
