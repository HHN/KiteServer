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
        newNovel.setEnvironmentWallID(request.getEnvironmentWallID());
        newNovel.setEnvironmentFloorID(request.getEnvironmentFloorID());
        newNovel.setEnvironmentWindowID(request.getEnvironmentWindowID());
        newNovel.setEnvironmentFurnitureID(request.getEnvironmentFurnitureID());
        newNovel.setPlayerCharacterFaceID(request.getPlayerCharacterFaceID());
        newNovel.setPlayerCharacterHairID(request.getPlayerCharacterHairID());
        newNovel.setPlayerCharacterBodyID(request.getPlayerCharacterBodyID());
        newNovel.setPlayerCharacterClothsID(request.getPlayerCharacterClothsID());
        newNovel.setOpponentCharacterFaceID(request.getOpponentCharacterFaceID());
        newNovel.setOpponentCharacterHairID(request.getOpponentCharacterHairID());
        newNovel.setOpponentCharacterBodyID(request.getOpponentCharacterBodyID());
        newNovel.setOpponentCharacterClothsID(request.getOpponentCharacterClothsID());
        newNovel.setDialog(request.getDialog());

        ResultCode code = novelService.saveNovel(newNovel);
        return code;
    }

    public List<VisualNovel> getNovels() {
        return novelService.getAllNovels();
    }
}
