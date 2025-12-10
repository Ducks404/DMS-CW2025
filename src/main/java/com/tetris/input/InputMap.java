package com.tetris.input;

import javafx.beans.Observable;
import javafx.beans.property.MapProperty;
import javafx.scene.input.KeyCode;

import java.util.Map;
import java.util.Set;

/**
 * Interface for managing keyboard key bindings to game events.
 * <p>
 * Defines operations to bind keys to events, retrieve events by key,
 * and access the underlying mapping as an observable property.
 * </p>
 */
public interface InputMap {

    /**
     * Gets the event type associated with a keyboard key.
     *
     * @param key the key code to look up
     * @return the associated event type, or null if no binding exists
     */
    EventType get(KeyCode key);

    /**
     * Binds a key code to an event type.
     * If the event is already bound to a different key, the old binding is removed.
     *
     * @param key the key code to bind
     * @param eventType the event type to bind to
     * @return 1 if the event was not previously bound, 0 if it was rebinded
     */
    int bind(KeyCode key, EventType eventType);

    /**
     * Checks if a key has a binding.
     *
     * @param key the key code to check
     * @return true if the key is bound, false otherwise
     */
    boolean has(KeyCode key);

    /**
     * Gets all key-event bindings as a set of entries.
     *
     * @return the set of key-event mapping entries
     */
    Set<Map.Entry<KeyCode, EventType>> entrySet();

    /**
     * Gets the observable map property for the key-event bindings.
     *
     * @return the MapProperty containing the bindings
     */
    MapProperty<KeyCode, EventType> mapProperty();
}
