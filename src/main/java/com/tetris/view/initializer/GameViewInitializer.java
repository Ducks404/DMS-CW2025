package com.tetris.view.initializer;

/**
 * Interface for game view initialization.
 * <p>
 * Defines the contract for setting up game display panes and configuring
 * their renderers and layout properties.
 * </p>
 */
public interface GameViewInitializer {
    /**
     * Sets up all game display panes.
     * <p>
     * Configures the game panel, brick panel, ghost panel, and notification group
     * for displaying the game board and brick movements.
     * </p>
     */
    void setupGamePanes();
}
