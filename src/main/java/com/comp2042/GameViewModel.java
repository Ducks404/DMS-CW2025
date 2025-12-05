package com.comp2042;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.input.KeyEvent;

public class GameViewModel implements ViewModel {
    private final InputEventListener eventListener;
    private final BaseInputMap inputMap;
    private final GameModel gameModel;

    private final ReadOnlyBooleanWrapper isPause = new ReadOnlyBooleanWrapper();
    private final ReadOnlyBooleanWrapper isGameOver = new ReadOnlyBooleanWrapper();

    public GameViewModel(InputEventListener eventListener, GameModel gameModel){
        this.eventListener = eventListener;
        this.inputMap = new GameInputMap();
        this.gameModel = gameModel;

        isPause.bind(gameModel.pauseProperty());
        isGameOver.bind(gameModel.gameOverProperty());
    }

    @Override
    public void handleKey(KeyEvent keyEvent) {
        eventListener.handleEvent(inputMap.get(keyEvent.getCode()));
        keyEvent.consume();
    }

    public ReadOnlyBooleanProperty pauseProperty() {
        return isPause.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty gameOverProperty() {
        return isGameOver.getReadOnlyProperty();
    }
}
