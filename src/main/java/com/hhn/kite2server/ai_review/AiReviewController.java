package com.hhn.kite2server.ai_review;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "aireview")
@AllArgsConstructor
public class AiReviewController {
    private final AiReviewService aiReviewService;

    @GetMapping
    public Response getAllAiReviews() {
        return aiReviewService.getAllReviews();
    }

    @PostMapping
    public Response addAiReview(@RequestBody AddAiReviewRequest request) {
        return aiReviewService.addAiReview(request);
    }

    @DeleteMapping
    public Response deleteAiReview(@RequestBody RemoveAiReviewRequest request) {
        return aiReviewService.removeAiReview(request);
    }
}
