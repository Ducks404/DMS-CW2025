package com.tetris.logic;

import com.tetris.input.InputMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SettingsModel {

    private final ObjectProperty<InputMap> inputMap = new SimpleObjectProperty<>();

    public void setInputMap(InputMap inputMap) {
        if (inputMap == null) {
            throw new IllegalArgumentException("InputMap cannot be set to null!");
        }
        this.inputMap.set(inputMap);
    }

    public ObjectProperty<InputMap> inputMapProperty() {
        return inputMap;
    }

    public InputMap getInputMap() {
        return inputMap.get();
    }
}
