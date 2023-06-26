package com.hhn.kite2server.novelposting;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "novels")
@AllArgsConstructor
public class NovelsController {

    private final NovelReadAndWriteService postingService;

    @PostMapping
    public Response post(@RequestBody NovelPostingRequest request) {
        Response response = new Response();
        ResultCode resultCode = postingService.post(request);
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
}
