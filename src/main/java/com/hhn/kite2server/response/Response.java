package com.hhn.kite2server.response;

import com.hhn.kite2server.account.comment.CommentInformation;
import com.hhn.kite2server.novels.VisualNovel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int resultCode;
    private String resultText;
    private String authToken;
    private String refreshToken;
    private String completion;
    private List<VisualNovel> novels;
    private List<CommentInformation> comments;
    public VisualNovel specifiedNovel;
}
