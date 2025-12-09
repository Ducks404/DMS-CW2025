package com.tetris.viewmodel;

import com.sun.scenario.Settings;
import com.tetris.controller.InputEventListener;
import com.tetris.input.BaseInputMap;
import com.tetris.input.EventType;
import com.tetris.input.GameInputMap;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.input.KeyEvent;

public class GameViewModel implements ViewModel {
    private final InputEventListener eventListener;
    private final ReadOnlyObjectProperty<InputMap> inputMapProperty;
    private final GameModel gameModel;

    public GameViewModel(InputEventListener eventListener, GameModel gameModel, SettingsModel settingsModel){
        this.eventListener = eventListener;
        this.gameModel = gameModel;
        this.inputMapProperty = settingsModel.inputMapProperty();
    }

    @Override
    public void handleKey(KeyEvent keyEvent) {
        EventType intent = getInputMap().get(keyEvent.getCode());
        if (intent != null) {
            eventListener.handleEvent(intent);
        }
        keyEvent.consume();
    }

    public void togglePause() {
        eventListener.handleEvent(EventType.PAUSE);
    }

    public ReadOnlyBooleanProperty pauseProperty() {
        return gameModel.pauseProperty();
    }

    public ReadOnlyBooleanProperty gameOverProperty() {
        return gameModel.gameOverProperty();
    }

    public ReadOnlyIntegerProperty scoreProperty() {
        return gameModel.scoreProperty();
    }

    public ReadOnlyObjectProperty<int[][]> nextBrickProperty() {
        return gameModel.nextBrickProperty();
    }

    public ReadOnlyObjectProperty<int[][]> holdBrickProperty() {
        return gameModel.holdBrickProperty();
    }

    private InputMap getInputMap() {
        if (inputMapProperty.get() == null) {
            throw new IllegalStateException("InputMap is set to null! Have not setInputMap!");
        }
        return inputMapProperty.get();
    }
}
