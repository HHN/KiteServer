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
}
