package com.tetris.input;

/**
 * Enumeration indicating the source of a game event.
 * <p>
 * Events can originate from user input via keyboard or from the game loop thread
 * when applying automatic gravity.
 * </p>
 */
public enum EventSource {
    /** Event triggered by direct user input. */
    USER,
    /** Event triggered by the game loop thread. */
    THREAD
}
