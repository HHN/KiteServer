package com.hhn.kite2server.novelposting;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.novels.VisualNovelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NovelReadAndWriteService {

    private final VisualNovelService novelService;

    public ResultCode post(NovelPostingRequest request) {
        VisualNovel newNovel = new VisualNovel();
        newNovel.setTitle(request.getTitle());
        newNovel.setDescription(request.getDescription());
        newNovel.setCreator(request.getCreator());
        ResultCode code = novelService.saveNovel(newNovel);
        return code;
    }

    public long GetCreatorOfNovel(long visualNovelId) {
        if (!novelService.doesExist(visualNovelId)) {
            return -1;
        }
        return novelService.loadNovelById(visualNovelId).getCreator();
    }

    public List<VisualNovel> getNovels() {
        return novelService.getAllNovels();
    }


    public ResultCode delete(long id) {
        if (!novelService.doesExist(id)) {
            return ResultCode.NOVEL_NOT_FOUND;
        }
        return novelService.delete(novelService.loadNovelById(id));
    }
}
