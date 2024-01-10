package com.hhn.kite2server.reviewobserver;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "reviewobserver")
@AllArgsConstructor
public class ReviewObserverController {

    private final ReviewObserverService reviewObserverService;

    @GetMapping
    public Response getAllReviewObservers() {
        return reviewObserverService.getAllReviewObservers();
    }

    @PostMapping
    public Response addReviewObserver(@RequestBody AddReviewObserverRequest request) {
        return reviewObserverService.addReviewObserver(request);
    }

    @DeleteMapping
    public Response deleteReviewObserver(@RequestBody RemoveReviewObserverRequest request) {
        return reviewObserverService.removeReviewObserver(request);
    }
}
