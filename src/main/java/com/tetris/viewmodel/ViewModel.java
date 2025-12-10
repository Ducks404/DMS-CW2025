package com.tetris.viewmodel;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.input.KeyEvent;

/**
 * Interface for a game view model.
 * <p>
 * Defines the contract for view models that provide access to game state
 * and handle user input events. Acts as a bridge between the UI and game logic.
 * </p>
 */
public interface ViewModel {

    /**
     * Handles a keyboard key event from the UI.
     * <p>
     * Converts the key event to a game action and forwards it to the controller.
     * </p>
     *
     * @param keyEvent the keyboard event to handle
     */
    void handleKey(KeyEvent keyEvent);

    /**
     * Gets the pause state property.
     *
     * @return a read-only boolean property indicating if the game is paused
     */
    ReadOnlyBooleanProperty pauseProperty();

    /**
     * Gets the game over state property.
     *
     * @return a read-only boolean property indicating if the game is over
     */
    ReadOnlyBooleanProperty gameOverProperty();

    /**
     * Gets the current score property.
     *
     * @return a read-only integer property containing the current score
     */
    ReadOnlyIntegerProperty scoreProperty();

    /**
     * Gets the next brick shape property.
     *
     * @return a read-only object property containing the next brick's shape matrix
     */
    ReadOnlyObjectProperty<int[][]> nextBrickProperty();

    /**
     * Gets the held brick shape property.
     *
     * @return a read-only object property containing the held brick's shape matrix
     */
    ReadOnlyObjectProperty<int[][]> holdBrickProperty();

    /**
     * Called when the user requests to change key bindings.
     * <p>
     * Typically pauses the game to prevent interference during remapping.
     * </p>
     */
    void onChangeKeybindButtonPressed();
}
