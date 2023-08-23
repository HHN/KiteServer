package com.hhn.kite2server.novels;

import com.hhn.kite2server.account.comment.CommentService;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VisualNovelService {

    private final VisualNovelRepository visualNovelRepository;
    private final CommentService commentService;

    public VisualNovel loadNovelById(Long id) {
        return visualNovelRepository.findById(id).get();
    }

    public List<VisualNovel> getAllNovels() {
        return visualNovelRepository.findAll();
    }

    public ResultCode saveNovel(VisualNovel novel) {
        visualNovelRepository.save(novel);
        return ResultCode.SUCCESSFULLY_POSTED_NOVEL;
    }

    public ResultCode delete(VisualNovel novel) {
        visualNovelRepository.delete(novel);
        commentService.deleteAllCommentsOfVisualNovel(novel);
        return ResultCode.SUCCESSFULLY_DELETED_NOVEL;
    }

    public boolean doesExist(long id) {
        return visualNovelRepository.existsById(id);
    }

    public void deleteNovelsFromUser(AppUser user) {
        visualNovelRepository.deleteNovelsFromUser(user);
    }
}
