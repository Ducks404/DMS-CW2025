package com.tetris.logic;

import com.tetris.input.InputMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Model class for game settings management.
 * <p>
 * Maintains the input key bindings for the game as an observable property.
 * The input map defines which keyboard keys map to game events.
 * </p>
 */
public class SettingsModel {

    /** Property tracking the current input key bindings map. */
    private final ObjectProperty<InputMap> inputMap = new SimpleObjectProperty<>();

    /**
     * Sets the input map for key bindings.
     *
     * @param inputMap the InputMap to set (cannot be null)
     * @throws IllegalArgumentException if inputMap is null
     */
    public void setInputMap(InputMap inputMap) {
        if (inputMap == null) {
            throw new IllegalArgumentException("InputMap cannot be set to null!");
        }
        this.inputMap.set(inputMap);
    }

    /**
     * Returns the input map property.
     *
     * @return the ObjectProperty containing the InputMap
     */
    public ObjectProperty<InputMap> inputMapProperty() {
        return inputMap;
    }

    /**
     * Gets the current input map.
     *
     * @return the current InputMap
     */
    public InputMap getInputMap() {
        return inputMap.get();
    }
}
