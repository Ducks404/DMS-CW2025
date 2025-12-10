package com.tetris.viewmodel;

import com.tetris.controller.InputEventListener;
import com.tetris.input.EventType;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.input.KeyEvent;

/**
 * View model for the game UI.
 * <p>
 * Acts as a mediator between the JavaFX UI and the game logic.
 * Provides read-only properties for game state and handles keyboard input
 * by routing it through the input map to the event listener.
 * </p>
 */
public class GameViewModel implements ViewModel {
    /** The controller handling game events and logic */
    private final InputEventListener eventListener;
    /** Property containing the current input key-to-event mapping */
    private final ReadOnlyObjectProperty<InputMap> inputMapProperty;
    /** The game state model containing mutable game properties */
    private final GameModel gameModel;

    /**
     * Constructs a GameViewModel.
     *
     * @param eventListener the controller to dispatch input events to
     * @param gameModel the game state model
     * @param settingsModel the settings containing the input map
     */
    public GameViewModel(InputEventListener eventListener, GameModel gameModel, SettingsModel settingsModel){
        this.eventListener = eventListener;
        this.gameModel = gameModel;
        this.inputMapProperty = settingsModel.inputMapProperty();
    }

    /**
     * Handles a keyboard key event by converting it to a game event.
     * <p>
     * Looks up the KeyCode in the input map to find the corresponding EventType,
     * then forwards it to the event listener for processing.
     * </p>
     *
     * @param keyEvent the keyboard event to handle
     */
    @Override
    public void handleKey(KeyEvent keyEvent) {
        EventType intent = getInputMap().get(keyEvent.getCode());
        if (intent != null) {
            eventListener.handleEvent(intent);
        }
        keyEvent.consume();
    }

    /**
     * Requests the game to toggle pause state.
     */
    private void togglePause() {
        eventListener.handleEvent(EventType.PAUSE);
    }

    /**
     * Gets the pause state property of the game.
     *
     * @return a read-only boolean property indicating if the game is paused
     */
    public ReadOnlyBooleanProperty pauseProperty() {
        return gameModel.pauseProperty();
    }

    /**
     * Gets the game over state property.
     *
     * @return a read-only boolean property indicating if the game is over
     */
    public ReadOnlyBooleanProperty gameOverProperty() {
        return gameModel.gameOverProperty();
    }

    /**
     * Gets the current score property.
     *
     * @return a read-only integer property containing the current score
     */
    public ReadOnlyIntegerProperty scoreProperty() {
        return gameModel.scoreProperty();
    }

    /**
     * Gets the next brick shape property.
     *
     * @return a read-only object property containing the next brick's shape matrix
     */
    public ReadOnlyObjectProperty<int[][]> nextBrickProperty() {
        return gameModel.nextBrickProperty();
    }

    /**
     * Gets the held brick shape property.
     *
     * @return a read-only object property containing the held brick's shape matrix
     */
    public ReadOnlyObjectProperty<int[][]> holdBrickProperty() {
        return gameModel.holdBrickProperty();
    }

    /**
     * Called when a key binding change button is pressed.
     * <p>
     * Pauses the game to allow key binding configuration without interference.
     * </p>
     */
    @Override
    public void onChangeKeybindButtonPressed() {
        togglePause();
    }

    /**
     * Gets the current input map from the settings.
     *
     * @return the active InputMap for keyboard-to-event mapping
     * @throws IllegalStateException if InputMap is null
     */
    private InputMap getInputMap() {
        if (inputMapProperty.get() == null) {
            throw new IllegalStateException("InputMap is set to null! Have not setInputMap!");
        }
        return inputMapProperty.get();
    }
}
