package com.hhn.kite2server.expert_feedback_answer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PostExpertFeedbackAnswerRequest {
    private String expertName;
    private String expertFeedbackAnswer;
    private Long idOfExpertFeedbackQuestion;

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getExpertFeedbackAnswer() {
        return expertFeedbackAnswer;
    }

    public void setExpertFeedbackAnswer(String expertFeedbackAnswer) {
        this.expertFeedbackAnswer = expertFeedbackAnswer;
    }

    public Long getIdOfExpertFeedbackQuestion() {
        return idOfExpertFeedbackQuestion;
    }

    public void setIdOfExpertFeedbackQuestion(Long idOfExpertFeedbackQuestion) {
        this.idOfExpertFeedbackQuestion = idOfExpertFeedbackQuestion;
    }
}
