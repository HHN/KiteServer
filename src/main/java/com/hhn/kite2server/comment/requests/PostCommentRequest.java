package com.hhn.kite2server.comment.requests;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PostCommentRequest {
    private String comment;
    private Long visualNovelId;
}
