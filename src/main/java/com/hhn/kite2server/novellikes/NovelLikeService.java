package com.hhn.kite2server.novellikes;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.novels.VisualNovelService;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NovelLikeService {

    private final NovelLikeRepository novelLikeRepository;
    private final VisualNovelService novelService;

    public Response getNovelLikeInformation(AppUser user, GetNovelLikeInformationRequest request) {
        Response response = new Response();

        if (request == null) {
            ResultCode code = ResultCode.FAILED_TO_LIKE_NOVEL;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<VisualNovel> optionalVisualNovel = novelService.findVisualNovelById(request.getId());

        if (!optionalVisualNovel.isPresent() && request.getId() >= 0) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }

        ResultCode code = ResultCode.SUCCESSFULLY_GOT_NOVEL_LIKE_INFORMATION;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        getNovelLikeInformation(response, user, request.getId());
        return response;
    }

    private void getNovelLikeInformation(Response response, AppUser user, long requestId) {
        List<NovelLike> novelLikes = novelLikeRepository.findByVisualNovelId(requestId);
        response.setNumberOfNovelLikes(novelLikes.size());

        if (user == null) {
            response.novelLikedByUser = false;
            return;
        }
        Optional<NovelLike> optionalNovelLike = novelLikeRepository.findByUserAndVisualNovelId(user, requestId);
        if (optionalNovelLike.isPresent()) {
            response.novelLikedByUser = true;
        }
    }

    public Response likeNovel(AppUser user, LikeNovelRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_LIKE_NOVEL;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<VisualNovel> optionalVisualNovel = novelService.findVisualNovelById(request.getId());

        if (!optionalVisualNovel.isPresent() && request.getId() >= 0) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        NovelLike novelLike = new NovelLike();
        novelLike.setUser(user);
        novelLike.setVisualNovelId(request.getId());
        novelLikeRepository.save(novelLike);

        ResultCode code = ResultCode.SUCCESSFULLY_LIKED_NOVEL;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        getNovelLikeInformation(response, user, request.getId());
        return response;
    }

    public Response unlikeNovel(AppUser user, UnlikeNovelRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_UNLIKE_NOVEL;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        Optional<VisualNovel> optionalVisualNovel = novelService.findVisualNovelById(request.getId());

        if (!optionalVisualNovel.isPresent() && request.getId() >= 0) {
            ResultCode code = ResultCode.NOVEL_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }

        Optional<NovelLike> optionalNovelLike = novelLikeRepository.findByUserAndVisualNovelId(user, request.getId());

        if (!optionalNovelLike.isPresent()) {
            ResultCode code = ResultCode.NOVEL_LIKE_NOT_FOUND;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
            return response;
        }
        novelLikeRepository.delete(optionalNovelLike.get());
        ResultCode code = ResultCode.SUCCESSFULLY_UNLIKED_NOVEL;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        getNovelLikeInformation(response, user, request.getId());
        return response;
    }

    public void deleteLikesByUser(AppUser user) {
        novelLikeRepository.deleteByUser(user);
    }

    public void deleteLikesByNovel(Long id) {
        novelLikeRepository.deleteByVisualNovelId(id);
    }
}
