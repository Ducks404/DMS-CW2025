package com.comp2042;

import javafx.scene.input.KeyEvent;

public class GameViewModel implements ViewModel {
    private final InputEventListener eventListener;
    private final BaseInputMap inputMap;

    public GameViewModel(InputEventListener eventListener){
        this.eventListener = eventListener;
        this.inputMap = new GameInputMap();
    }

    @Override
    public void handleKey(KeyEvent keyEvent) {
        eventListener.handleEvent(inputMap.get(keyEvent.getCode()));
        keyEvent.consume();
    }
}
