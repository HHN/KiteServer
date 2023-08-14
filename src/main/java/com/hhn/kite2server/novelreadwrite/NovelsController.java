package com.hhn.kite2server.novelreadwrite;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "novels")
@AllArgsConstructor
public class NovelsController {

    private final NovelService postingService;

    @PostMapping
    public Response post(@AuthenticationPrincipal AppUser user, @RequestBody NovelPostingRequest request) {
        Response response = new Response();
        ResultCode resultCode = postingService.post(user, request);
        response.setResultText(resultCode.toString());
        response.setResultCode(resultCode.toInt());
        return response;
    }

    @GetMapping
    public Response getNovels() {
        Response response = new Response();
        List<VisualNovel> novels = postingService.getNovels();

        if (novels.isEmpty()) {
            response.setResultCode(ResultCode.NO_NOVEL_AVAILABLE.toInt());
            response.setResultText(ResultCode.NO_NOVEL_AVAILABLE.toString());
        } else {
            response.setNovels(novels);
            response.setResultCode(ResultCode.SUCCESSFULLY_GOT_NOVELS.toInt());
            response.setResultText(ResultCode.SUCCESSFULLY_GOT_NOVELS.toString());
        }
        return response;
    }

    @DeleteMapping
    public Response delete(@AuthenticationPrincipal AppUser user, @RequestBody NovelDeleteRequest request) {
        Response response = new Response();

        if (postingService.getCreatorOfNovel(request.getNovelId()) == -1) {
            response.setResultCode(ResultCode.NOVEL_NOT_FOUND.toInt());
            response.setResultText(ResultCode.NOVEL_NOT_FOUND.toString());

        } else if (user.getId() == postingService.getCreatorOfNovel(request.getNovelId())) {
            ResultCode code = postingService.delete((request.getNovelId()));
            response.setResultCode((code.toInt()));
            response.setResultText(code.toString());

        } else {
            response.setResultCode(ResultCode.NOT_AUTHORIZED.toInt());
            response.setResultText(ResultCode.NOT_AUTHORIZED.toString());
        }
        return response;
    }
}
