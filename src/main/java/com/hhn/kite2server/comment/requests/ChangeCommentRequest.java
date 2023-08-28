package com.hhn.kite2server.comment.requests;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangeCommentRequest {
    private long id;
    private String comment;
}
