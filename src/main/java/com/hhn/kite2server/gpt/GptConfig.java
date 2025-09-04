package com.hhn.kite2server.gpt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GptConfig {
    @Value("${openai.api.key}")
    String apiKey;

    @Value("${openai.model:gpt-4o}")
    String model;

    @Value("${openai.temperature:0.5}")
    double temperature;

    @Value("${openai.max-tokens:2000}")
    int maxTokens;
}
