package com.hhn.kite2server.response;

import com.hhn.kite2server.ai_review.AiReview;
import com.hhn.kite2server.data.DataObject;
import com.hhn.kite2server.expert_feedback_answer.ExpertFeedbackAnswer;
import com.hhn.kite2server.expert_feedback_questions.ExpertFeedbackQuestion;
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
    private List<DataObject> dataObjects;
    private int version;
    private int userRole;
    private List<ExpertFeedbackQuestion> expertFeedbackQuestions;
    private List<ExpertFeedbackAnswer> expertFeedbackAnswers;
}
