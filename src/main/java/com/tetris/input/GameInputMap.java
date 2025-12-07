package com.tetris.input;

import javafx.scene.input.KeyCode;

public class GameInputMap extends BaseInputMap {
    public GameInputMap() {
        bind(KeyCode.UP, EventType.MOVE_ROTATE);
        bind(KeyCode.LEFT, EventType.MOVE_LEFT);
        bind(KeyCode.RIGHT, EventType.MOVE_RIGHT);
        bind(KeyCode.DOWN, EventType.MOVE_DOWN);
        bind(KeyCode.P, EventType.PAUSE);
        bind(KeyCode.N, EventType.NEW_GAME);
        bind(KeyCode.H, EventType.HOLD);
        bind(KeyCode.SPACE, EventType.HARD_DROP);
    }
}
