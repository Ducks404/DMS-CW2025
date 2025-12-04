package com.comp2042;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class GameViewModel implements ViewModel {
    private final InputEventListener eventListener;

    public GameViewModel(InputEventListener eventListener){
        this.eventListener = eventListener;
    }

    @Override
    public void handleKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
            eventListener.handleEvent(EventType.MOVE_LEFT);
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            eventListener.handleEvent(EventType.MOVE_RIGHT);
        }
        else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            eventListener.handleEvent(EventType.MOVE_ROTATE);
        }
        else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            eventListener.handleEvent(EventType.MOVE_DOWN);
        }
        else if (keyEvent.getCode() == KeyCode.P) {
            eventListener.handleEvent(EventType.PAUSE);
        }
        else if (keyEvent.getCode() == KeyCode.N) {
            eventListener.handleEvent(EventType.NEW_GAME);
        }
        keyEvent.consume();
    }
}
