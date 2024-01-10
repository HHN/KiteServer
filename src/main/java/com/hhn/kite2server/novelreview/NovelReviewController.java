package com.hhn.kite2server.novelreview;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "novelreview")
@AllArgsConstructor
public class NovelReviewController {

    private final NovelReviewService novelReviewService;

    @GetMapping
    public Response getAllNovelReviews() {
        return novelReviewService.getAllReviews();
    }

    @PostMapping
    public Response addNovelReview(@RequestBody AddNovelReviewRequest request) {
        return novelReviewService.addNovelReview(request);
    }

    @DeleteMapping
    public Response deleteNovelReview(@RequestBody RemoveNovelReviewRequest request) {
        return novelReviewService.removeNovelReview(request);
    }
}
