package com.tetris.input;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BaseInputMap implements InputMap {
    final ObservableMap<KeyCode, EventType> map = FXCollections.observableMap(new LinkedHashMap<>());
    final MapProperty<KeyCode, EventType> mapProperty = new SimpleMapProperty<>(map);

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

    @Override
    public MapProperty<KeyCode, EventType> mapProperty() {
        return mapProperty;
    }
}
