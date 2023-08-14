package com.hhn.kite2server.novelreadwrite;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class FindNovelRequest {
    private String query;
}