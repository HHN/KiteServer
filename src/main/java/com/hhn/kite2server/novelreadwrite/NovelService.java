package com.hhn.kite2server.novelreadwrite;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novels.VisualNovel;
import com.hhn.kite2server.novels.VisualNovelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NovelService {

    private final VisualNovelService novelService;
    private final AppUserService appUserService;

    public ResultCode post(AppUser user, NovelPostingRequest request) {
        VisualNovel newNovel = new VisualNovel();
        newNovel.setTitle(request.getTitle());
        newNovel.setDescription(request.getDescription());
        newNovel.setCreator(user);
        ResultCode code = novelService.saveNovel(newNovel);
        return code;
    }

    public long getCreatorOfNovel(long visualNovelId) {
        if (!novelService.doesExist(visualNovelId)) {
            return -1;
        }
        return novelService.loadNovelById(visualNovelId).getCreator().getId();
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

    public void deleteAllNovelsOfUser(AppUser user) {
        novelService.deleteNovelsFromUser(user);
    }

    public VisualNovel findNovel(String query) {
        try {
            long id = Long.parseLong(query);
            if (novelService.doesExist(id)) {
                return novelService.loadNovelById(id);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
