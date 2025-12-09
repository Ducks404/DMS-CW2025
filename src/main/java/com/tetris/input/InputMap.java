package com.tetris.input;

import javafx.scene.input.KeyCode;

import java.util.Map;
import java.util.Set;

public interface InputMap {
    EventType get(KeyCode key);
    int bind(KeyCode key, EventType eventType);
    boolean has(KeyCode key);
    Set<Map.Entry<KeyCode, EventType>> entrySet();
}
