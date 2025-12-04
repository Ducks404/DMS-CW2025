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
            eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
        }
        else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
        }
        else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
        }
        else if (keyEvent.getCode() == KeyCode.P) {
            eventListener.onPauseEvent();
        }
        else if (keyEvent.getCode() == KeyCode.N) {
            eventListener.createNewGame();
        }

        keyEvent.consume();
    }
}
