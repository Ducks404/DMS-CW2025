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

/**
 * Abstract base class implementing the InputMap interface.
 * <p>
 * Provides default implementations of key binding management using an observable map.
 * Subclasses should populate the map with initial key bindings in their constructors.
 * </p>
 */
public class BaseInputMap implements InputMap {

    /** Observable map storing key-event bindings. */
    final ObservableMap<KeyCode, EventType> map = FXCollections.observableMap(new LinkedHashMap<>());

    /** Property wrapping the observable map. */
    final MapProperty<KeyCode, EventType> mapProperty = new SimpleMapProperty<>(map);

    /**
     * Gets the event type associated with a key, if it exists.
     *
     * @param key the key code to look up
     * @return the associated event type, or null if not found
     */
    @Override
    public EventType get(KeyCode key) {
        if (has(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    /**
     * Binds a key to an event type, removing any previous binding of that event.
     *
     * @param key the key code to bind
     * @param eventType the event type to bind to
     * @return 1 if the event was not previously bound, 0 otherwise
     */
    @Override
    public int bind(KeyCode key, EventType eventType) {
        if (!map.containsValue(eventType)) {
            return 1;
        }
        map.remove(keyOf(eventType));
        map.put(key, eventType);
        return 0;
    }

    /**
     * Finds the key bound to a given event type.
     *
     * @param eventType the event type to search for
     * @return the key code bound to the event, or null if not found
     */
    private KeyCode keyOf(EventType eventType){
        for (var entry: map.entrySet()) {
            if (entry.getValue()== eventType) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if a key has a binding.
     *
     * @param key the key code to check
     * @return true if the key is bound, false otherwise
     */
    @Override
    public boolean has(KeyCode key) {
        return map.containsKey(key);
    }

    /**
     * Gets all key-event bindings as a set of entries.
     *
     * @return the set of entries in the binding map
     */
    @Override
    public Set<Map.Entry<KeyCode, EventType>> entrySet() {
        return map.entrySet();
    }

    /**
     * Gets the observable map property.
     *
     * @return the MapProperty for the key-event bindings
     */
    @Override
    public MapProperty<KeyCode, EventType> mapProperty() {
        return mapProperty;
    }
}
