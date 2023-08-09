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
    private Long creator;
}
