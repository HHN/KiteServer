package com.hhn.kite2server.analytics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SceneType {
    START_SCENE("StartScene"),
    EINSTIEGSNOVEL("Einstiegsnovel"),
    FOUNDERS_BUBBLE("FoundersBubble"),
    FEEDBACK("Feedback");

    private final String wireName;

    SceneType(String wireName) { this.wireName = wireName; }

    @JsonValue
    public String toValue() { return wireName; }

    @JsonCreator
    public static SceneType fromValue(String value) {
        for (SceneType t : values()) {
            if (t.wireName.equalsIgnoreCase(value)) return t;
        }
        throw new IllegalArgumentException("Unbekannte Szene: " + value);
    }
}
