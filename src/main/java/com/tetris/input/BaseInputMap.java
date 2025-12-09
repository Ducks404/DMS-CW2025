package com.tetris.input;

import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        if (!map.containsValue(eventType)) {
            return 1;
        }
        map.remove(keyOf(eventType));
        map.put(key, eventType);
        return 0;
    }

    private KeyCode keyOf(EventType eventType){
        for (var entry: map.entrySet()) {
            if (entry.getValue()== eventType) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean has(KeyCode key) {
        return map.containsKey(key);
    }

    @Override
    public Set<Map.Entry<KeyCode, EventType>> entrySet() {
        return map.entrySet();
    }
}
