package com.hhn.kite2server.gpt;

import lombok.*;

/**
 * Data record for an AI completion request.
 * @param prompt The actual text input for the AI.
 * @param verification A honeypot field used to detect automated bot requests. Should be left empty.
 */
public record GptRequest(
    String prompt,
    String verification // Honeypot-Field, to detect bots (should be left empty by real users)
){}
