package com.hhn.kite2server.novels;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class VisualNovel {

    @SequenceGenerator(name = "novel_sequence", sequenceName = "novel_sequence", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "novel_sequence")

    private Long id;
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
    @Column(columnDefinition="text") private String dialog;
}
