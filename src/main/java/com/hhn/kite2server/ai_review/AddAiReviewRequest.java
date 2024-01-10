package com.hhn.kite2server.ai_review;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AddAiReviewRequest {
    private Long novelId;
    private String novelName;
    private String prompt;
    private String aiFeedback;
    private String reviewText;
}
