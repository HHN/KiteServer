package com.hhn.kite2server.account.comment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CommentInformation {
    private Long id;
    private String comment;
    private String author;
    private Long likeCount;
    private boolean liked;
}
