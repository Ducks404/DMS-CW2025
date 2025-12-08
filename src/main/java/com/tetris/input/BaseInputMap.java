package com.tetris.input;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

public class BaseInputMap implements InputMap {
    final Map<KeyCode, EventType> map = new HashMap<>();

    @Override
    public EventType get(KeyCode key) {
        if (has(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    @Override
    public int bind(KeyCode key, EventType eventType) {
        if (!has(key)) {
            return 1;
        }
        map.put(key, eventType);
        return 0;
    }

    @Override
    public boolean has(KeyCode key) {
        return map.containsKey(key);
    }
}
