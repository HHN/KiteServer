package com.hhn.kite2server.novelreadwrite;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "novel")
@AllArgsConstructor
public class FindNovelController {

    private final NovelService postingService;

    @GetMapping
    public Response getNovel(@RequestBody FindNovelRequest request) {
        Response response = new Response();
        VisualNovel novel = postingService.findNovel(request.getQuery());

        if (novel == null) {
            response.setResultCode(ResultCode.NOVEL_NOT_FOUND.toInt());
            response.setResultText(ResultCode.NOVEL_NOT_FOUND.toString());
        } else {
            response.setSpecifiedNovel(novel);
            response.setResultCode(ResultCode.SUCCESSFULLY_FOUND_NOVEL.toInt());
            response.setResultText(ResultCode.SUCCESSFULLY_FOUND_NOVEL.toString());
        }
        return response;
    }
}
