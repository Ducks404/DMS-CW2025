package com.tetris.input;

/**
 * Enumeration of game events that can be triggered by user input or game logic.
 * <p>
 * Each event type represents an action that can occur in the game,
 * such as brick movement, rotation, pausing, or game mode transitions.
 * </p>
 */
public enum EventType {
    /** Soft drop (accelerate brick downward). */
    MOVE_DOWN("Soft Drop"),
    /** Move brick left. */
    MOVE_LEFT("Move Left"),
    /** Move brick right. */
    MOVE_RIGHT("Move Right"),
    /** Rotate brick. */
    MOVE_ROTATE("Rotate"),
    /** Pause/resume game. */
    PAUSE("Pause"),
    /** Start new game. */
    NEW_GAME("New Game"),
    /** Hold current brick. */
    HOLD("Hold"),
    /** Hard drop (instant placement). */
    HARD_DROP("Hard Drop"),
    /** Exit to main menu. */
    EXIT("Exit"),
    /** Move brick up (creative mode). */
    MOVE_UP("Move up"),
    /** Manually place brick (creative mode). */
    PLACE("Place"),
    /** Toggle between edit/play modes (creative mode). */
    TOGGLE_PLAY("Play/Edit"),
    /** Clear board (creative mode). */
    CLEAR("Clear");

    /** Display name for the event. */
    private final String displayName;

    /**
     * Constructs an EventType with a display name.
     *
     * @param displayName the human-readable name for this event
     */
    EventType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name for this event type.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
