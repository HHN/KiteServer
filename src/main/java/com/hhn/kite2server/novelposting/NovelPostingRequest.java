package com.hhn.kite2server.novelposting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NovelPostingRequest {
    private String title;
    private String description;
    private Long environmentWallID;
    private Long environmentFloorID;
    private Long environmentWindowID;
    private Long environmentFurnitureID;
    private Long playerCharacterFaceID;
    private Long playerCharacterHairID;
    private Long playerCharacterBodyID;
    private Long playerCharacterClothsID;
    private Long opponentCharacterFaceID;
    private Long opponentCharacterHairID;
    private Long opponentCharacterBodyID;
    private Long opponentCharacterClothsID;
    private String dialog;
}
