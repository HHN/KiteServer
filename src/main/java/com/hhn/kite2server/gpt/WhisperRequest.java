package com.hhn.kite2server.gpt;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class WhisperRequest {
    private byte[] file;
}
