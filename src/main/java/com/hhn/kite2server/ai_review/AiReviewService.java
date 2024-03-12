package com.hhn.kite2server.ai_review;

import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.email.EmailCreatorService;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.reviewobserver.ReviewObserverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AiReviewService {

    private final AiReviewRepository aiReviewRepository;
    private final EmailCreatorService emailCreatorService;
    private final ReviewObserverService reviewObserverService;

    public Response getAllReviews() {
        Response response = new Response();
        List<AiReview> reviews = aiReviewRepository.findAll();
        response.setAiReviews(reviews);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_ALL_AI_REVIEWS;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response addAiReview(AddAiReviewRequest request) {
        AiReview aiReview = new AiReview();
        aiReview.setNovelId(request.getNovelId());
        aiReview.setNovelName(request.getNovelName());
        aiReview.setReviewText(request.getReviewText());
        aiReview.setAiFeedback(request.getAiFeedback());
        aiReview.setPrompt(request.getPrompt());
        saveReview(aiReview);

        Response response = new Response();
        ResultCode code = ResultCode.SUCCESSFULLY_ADDED_AI_REVIEW;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());

        String email = emailCreatorService.buildEmailForNotificationAboutAiReview(request.getNovelName(),
                request.getPrompt(), request.getAiFeedback(), request.getReviewText());
        reviewObserverService.SendEmailToAllObserversAboutAiReview(email);

        return response;
    }

    public Response removeAiReview(RemoveAiReviewRequest request) {
        Response response = new Response();
        boolean success = deleteReview(request.getId());

        ResultCode code;

        if (success) {
            code = ResultCode.SUCCESSFULLY_DELETED_AI_REVIEW;
        } else {
            code = ResultCode.NO_SUCH_AI_REVIEW;
        }
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public boolean deleteReview(Long id) {
        if (!aiReviewRepository.findById(id).isPresent()) {
            return false;
        }
        aiReviewRepository.deleteById(id);
        return true;
    }

    public void saveReview(AiReview aiReview) {
        if (aiReview == null) {
            return;
        }
        aiReviewRepository.save(aiReview);
    }
}
