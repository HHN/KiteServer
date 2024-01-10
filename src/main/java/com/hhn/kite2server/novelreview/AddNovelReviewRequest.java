package com.hhn.kite2server.novelreview;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AddNovelReviewRequest {
    private Long novelId;
    private String novelName;
    private Long rating;
    private String reviewText;
}
