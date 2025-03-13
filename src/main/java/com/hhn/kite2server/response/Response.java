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

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public List<NovelReview> getNovelReviews() {
        return novelReviews;
    }

    public void setNovelReviews(List<NovelReview> novelReviews) {
        this.novelReviews = novelReviews;
    }

    public List<AiReview> getAiReviews() {
        return aiReviews;
    }

    public void setAiReviews(List<AiReview> aiReviews) {
        this.aiReviews = aiReviews;
    }

    public List<ReviewObserver> getReviewObservers() {
        return reviewObservers;
    }

    public void setReviewObservers(List<ReviewObserver> reviewObservers) {
        this.reviewObservers = reviewObservers;
    }

    public List<DataObject> getDataObjects() {
        return dataObjects;
    }

    public void setDataObjects(List<DataObject> dataObjects) {
        this.dataObjects = dataObjects;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public List<ExpertFeedbackQuestion> getExpertFeedbackQuestions() {
        return expertFeedbackQuestions;
    }

    public void setExpertFeedbackQuestions(List<ExpertFeedbackQuestion> expertFeedbackQuestions) {
        this.expertFeedbackQuestions = expertFeedbackQuestions;
    }

    public List<ExpertFeedbackAnswer> getExpertFeedbackAnswers() {
        return expertFeedbackAnswers;
    }

    public void setExpertFeedbackAnswers(List<ExpertFeedbackAnswer> expertFeedbackAnswers) {
        this.expertFeedbackAnswers = expertFeedbackAnswers;
    }
}
