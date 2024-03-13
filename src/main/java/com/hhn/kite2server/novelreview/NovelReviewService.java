package com.hhn.kite2server.novelreview;

import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.email.EmailCreatorService;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.reviewobserver.ReviewObserverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NovelReviewService {

    private final NovelReviewRepository novelReviewRepository;
    private final EmailCreatorService emailCreatorService;
    private final ReviewObserverService reviewObserverService;

    public Response getAllReviews() {
        Response response = new Response();
        List<NovelReview> reviews = novelReviewRepository.findAll();
        response.setNovelReviews(reviews);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_ALL_NOVEL_REVIEWS;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response addNovelReview(AddNovelReviewRequest request) {
        NovelReview novelReview = new NovelReview();
        novelReview.setNovelId(request.getNovelId());
        novelReview.setNovelName(request.getNovelName());
        novelReview.setReviewText(request.getReviewText());
        novelReview.setRating(request.getRating());
        saveReview(novelReview);

        Response response = new Response();
        ResultCode code = ResultCode.SUCCESSFULLY_ADDED_NOVEL_REVIEW;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());

        String email = emailCreatorService.buildEmailForNotificationAboutNovelReview(request.getNovelName(),
                String.valueOf(request.getRating()), request.getReviewText());
        reviewObserverService.SendEmailToAllObserversAboutNovelReview(email);

        return response;
    }

    public Response removeNovelReview(RemoveNovelReviewRequest request) {
        Response response = new Response();
        boolean success = deleteReview(request.getId());

        ResultCode code;

        if (success) {
            code = ResultCode.SUCCESSFULLY_DELETED_NOVEL_REVIEW;
        } else {
            code = ResultCode.NO_SUCH_NOVEL_REVIEW;
        }
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public boolean deleteReview(Long id) {
        if (novelReviewRepository.findById(id).isEmpty()) {
            return false;
        }
        novelReviewRepository.deleteById(id);
        return true;
    }

    public void saveReview(NovelReview novelReview) {
        if (novelReview == null) {
            return;
        }
        novelReviewRepository.save(novelReview);
    }
}
