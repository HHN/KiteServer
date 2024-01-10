package com.hhn.kite2server.response;

import com.hhn.kite2server.ai_review.AiReview;
import com.hhn.kite2server.comment.CommentInformation;
import com.hhn.kite2server.novelreview.NovelReview;
import com.hhn.kite2server.novels.VisualNovel;
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
    private String authToken;
    private String refreshToken;
    private String completion;
    private List<VisualNovel> novels;
    private List<CommentInformation> comments;
    public VisualNovel specifiedNovel;
    public int numberOfNovelLikes;
    public Boolean novelLikedByUser;
    public long score;
    public long money;
    private List<NovelReview> novelReviews;
    private List<AiReview> aiReviews;
    private List<ReviewObserver> reviewObservers;
}
