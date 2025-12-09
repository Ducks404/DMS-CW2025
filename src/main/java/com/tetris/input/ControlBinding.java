package com.tetris.input;

import javafx.scene.input.KeyCode;

public record ControlBinding(EventType eventType, KeyCode keyCode, String displayName) {

    public ControlBinding(EventType eventType, KeyCode keyCode){
        this(eventType, keyCode, eventType.getDisplayName());
    }
}
