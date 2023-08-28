package com.hhn.kite2server.novellikes;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"novellike"})
@AllArgsConstructor
public class NovelLikeController {

    private final NovelLikeService novelLikeService;

    @PostMapping
    public Response likeNovel(@AuthenticationPrincipal AppUser user, @RequestBody LikeNovelRequest request) {
        return novelLikeService.likeNovel(user, request);
    }

    @DeleteMapping
    public Response unlikeNovel(@AuthenticationPrincipal AppUser user, @RequestBody UnlikeNovelRequest request) {
        return novelLikeService.unlikeNovel(user, request);
    }

    @GetMapping
    public Response getNovelLikeInformation(@AuthenticationPrincipal AppUser user,
                                            @RequestBody GetNovelLikeInformationRequest request) {
        return novelLikeService.getNovelLikeInformation(user, request);
    }
}
