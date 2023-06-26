package com.hhn.kite2server.novels;

import com.hhn.kite2server.common.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VisualNovelService {

    private final VisualNovelRepository visualNovelRepository;

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
}
