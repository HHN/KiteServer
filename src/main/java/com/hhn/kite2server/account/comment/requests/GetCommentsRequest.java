package com.hhn.kite2server.account.comment.requests;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GetCommentsRequest {
    private Long visualNovelId;
}
