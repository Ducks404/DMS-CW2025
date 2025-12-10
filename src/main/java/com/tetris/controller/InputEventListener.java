package com.tetris.controller;

import com.tetris.input.EventType;

/**
 * Interface for handling input events in the game.
 * <p>
 * Implements the event listener pattern to process user input and thread-based events.
 * Classes implementing this interface are responsible for interpreting event types
 * and executing the corresponding game actions.
 * </p>
 */
public interface InputEventListener {

    /**
     * Handles a game event triggered by user input or game loop.
     *
     * @param eventType the type of event to handle
     */
    void handleEvent(EventType eventType);
}
