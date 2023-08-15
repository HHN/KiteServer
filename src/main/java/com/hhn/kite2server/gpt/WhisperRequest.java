package com.hhn.kite2server.gpt;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class WhisperRequest {
    private byte[] file;
}
