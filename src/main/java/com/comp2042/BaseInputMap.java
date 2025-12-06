package com.comp2042;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

public class BaseInputMap implements InputMap{
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
    public void bind(KeyCode key, EventType eventType) {
        map.put(key, eventType);
    }

    @Override
    public boolean has(KeyCode key) {
        return map.containsKey(key);
    }
}
