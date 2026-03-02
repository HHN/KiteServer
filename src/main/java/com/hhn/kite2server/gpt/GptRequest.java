package com.hhn.kite2server.gpt;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class GptRequest {
    private String prompt;

    // Honeypot-Field, to detect bots (should be left empty by real users)
    private String verification;
}
