package com.hhn.kite2server.gpt;

import lombok.*;

public record GptRequest(
    String prompt,
    String verification // Honeypot-Field, to detect bots (should be left empty by real users)
){}
