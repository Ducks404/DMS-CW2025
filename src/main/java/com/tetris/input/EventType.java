package com.tetris.input;

public enum EventType {
    MOVE_DOWN("Soft Drop"),
    MOVE_LEFT("Move Left"),
    MOVE_RIGHT("Move Right"),
    MOVE_ROTATE("Rotate"),
    PAUSE("Pause"),
    NEW_GAME("New Game"),
    HOLD("Hold"),
    HARD_DROP("Hard Drop");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
