package com.tetris.input;

import javafx.scene.input.KeyCode;

/**
 * Record representing a key binding for a game event.
 * <p>
 * Associates a keyboard key code with a game event type,
 * optionally with a custom display name.
 * </p>
 */
public record ControlBinding(EventType eventType, KeyCode keyCode, String displayName) {

    /**
     * Constructs a ControlBinding using the event type's default display name.
     *
     * @param eventType the game event type
     * @param keyCode the keyboard key code
     */
    public ControlBinding(EventType eventType, KeyCode keyCode){
        this(eventType, keyCode, eventType.getDisplayName());
    }
}
