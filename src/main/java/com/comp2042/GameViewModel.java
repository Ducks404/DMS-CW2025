package com.comp2042;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
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
        eventListener.handleEvent(inputMap.get(keyEvent.getCode()));
        keyEvent.consume();
    }

    public ReadOnlyBooleanProperty pauseProperty() {
        return gameModel.pauseProperty();
    }

    public ReadOnlyBooleanProperty gameOverProperty() {
        return gameModel.gameOverProperty();
    }
}
