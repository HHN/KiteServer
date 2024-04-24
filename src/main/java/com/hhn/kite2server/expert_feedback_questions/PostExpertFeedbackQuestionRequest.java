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
}
