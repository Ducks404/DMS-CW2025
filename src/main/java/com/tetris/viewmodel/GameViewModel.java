package com.tetris.viewmodel;

import com.tetris.controller.InputEventListener;
import com.tetris.input.BaseInputMap;
import com.tetris.input.EventType;
import com.tetris.input.GameInputMap;
import com.tetris.logic.GameModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.input.KeyEvent;

public class GameViewModel implements ViewModel {
    private final InputEventListener eventListener;
    private final BaseInputMap inputMap;
    private final GameModel gameModel;

    public GameViewModel(InputEventListener eventListener, GameModel gameModel){
        this.eventListener = eventListener;
        this.inputMap = new GameInputMap();
        this.gameModel = gameModel;
    }

    @Override
    public void handleKey(KeyEvent keyEvent) {
        EventType intent = inputMap.get(keyEvent.getCode());
        if (intent != null) {
            eventListener.handleEvent(intent);
        }
        keyEvent.consume();
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
}
