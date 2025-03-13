package com.hhn.kite2server.expert_feedback_questions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PostExpertFeedbackQuestionRequest {
    private Long novelId;
    private String novelName;
    private String userUuid;
    private String prompt;
    private String aiFeedback;
    private String dialogue;
    private String expertFeedbackQuestion;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getExpertFeedbackQuestion() {
        return expertFeedbackQuestion;
    }

    public void setExpertFeedbackQuestion(String expertFeedbackQuestion) {
        this.expertFeedbackQuestion = expertFeedbackQuestion;
    }
}
