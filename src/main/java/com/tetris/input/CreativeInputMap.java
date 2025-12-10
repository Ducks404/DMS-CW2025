package com.tetris.input;

import javafx.scene.input.KeyCode;

public class CreativeInputMap extends BaseInputMap {
    public CreativeInputMap() {
        map.put(KeyCode.W, EventType.MOVE_UP);
        map.put(KeyCode.LEFT, EventType.MOVE_LEFT);
        map.put(KeyCode.RIGHT, EventType.MOVE_RIGHT);
        map.put(KeyCode.DOWN, EventType.MOVE_DOWN);
        map.put(KeyCode.SPACE, EventType.HARD_DROP);
        map.put(KeyCode.UP, EventType.MOVE_ROTATE);
        map.put(KeyCode.F, EventType.PLACE);
        map.put(KeyCode.C, EventType.CLEAR);
        map.put(KeyCode.H, EventType.HOLD);
        map.put(KeyCode.ENTER, EventType.TOGGLE_PLAY);
        map.put(KeyCode.P, EventType.PAUSE);
        map.put(KeyCode.N, EventType.NEW_GAME);
        map.put(KeyCode.ESCAPE, EventType.EXIT);
    }
}
