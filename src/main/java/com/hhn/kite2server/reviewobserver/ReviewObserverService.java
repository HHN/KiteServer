package com.hhn.kite2server.reviewobserver;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewObserverService {

    private final ReviewObserverRepository reviewObserverRepository;

    public Response getAllReviewObservers() {
        Response response = new Response();
        List<ReviewObserver> reviewObserver = reviewObserverRepository.findAll();
        response.setReviewObservers(reviewObserver);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_ALL_REVIEW_OBSERVER;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response addReviewObserver(AddReviewObserverRequest request) {
        ReviewObserver reviewObserver = new ReviewObserver();
        reviewObserver.setEmail(request.getEmail());
        saveReviewObserver(reviewObserver);

        Response response = new Response();
        ResultCode code = ResultCode.SUCCESSFULLY_ADDED_REVIEW_OBSERVER;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response removeReviewObserver(RemoveReviewObserverRequest request) {
        Response response = new Response();
        boolean success = deleteReviewObserver(request.getId());

        ResultCode code;

        if (success) {
            code = ResultCode.SUCCESSFULLY_DELETED_REVIEW_OBSERVER;
        } else {
            code = ResultCode.NO_SUCH_REVIEW_OBSERVER;
        }
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public boolean deleteReviewObserver(Long id) {
        if (!reviewObserverRepository.findById(id).isPresent()) {
            return false;
        }
        reviewObserverRepository.deleteById(id);
        return true;
    }

    public void saveReviewObserver(ReviewObserver reviewObserver) {
        if (reviewObserver == null) {
            return;
        }
        reviewObserverRepository.save(reviewObserver);
    }
}
