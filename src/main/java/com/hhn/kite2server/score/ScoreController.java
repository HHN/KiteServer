package com.hhn.kite2server.score;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.money.GetMoneyRequest;
import com.hhn.kite2server.novelreadwrite.FindNovelRequest;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "score")
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping
    public Response getScore(@AuthenticationPrincipal AppUser user, @RequestBody GetScoreRequest request) {
        return scoreService.getScore(user, request);
    }

    @PostMapping
    public Response addToScore(@AuthenticationPrincipal AppUser user, @RequestBody AddScoreRequest request) {
        return scoreService.addToScore(user, request);
    }
}
