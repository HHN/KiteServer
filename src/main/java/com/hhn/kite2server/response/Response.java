package com.hhn.kite2server.response;

import com.hhn.kite2server.ai_review.AiReview;
import com.hhn.kite2server.novelreview.NovelReview;
import com.hhn.kite2server.reviewobserver.ReviewObserver;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int resultCode;
    private String resultText;
    private String completion;
    private List<NovelReview> novelReviews;
    private List<AiReview> aiReviews;
    private List<ReviewObserver> reviewObservers;
    private int version;
    private int userRole;
}
