package com.hhn.kite2server.novelreadwrite;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NovelPostingRequest {
    private String title;
    private String description;
}
