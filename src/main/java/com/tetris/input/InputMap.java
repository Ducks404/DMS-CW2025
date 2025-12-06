package com.tetris.input;

import javafx.scene.input.KeyCode;

public interface InputMap {
    EventType get(KeyCode key);
    void bind(KeyCode key, EventType eventType);
    boolean has(KeyCode key);
}
