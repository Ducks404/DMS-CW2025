package com.tetris.viewmodel;

import com.tetris.input.EventType;
import com.tetris.input.InputMap;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.*;
import javafx.scene.input.KeyCode;

public class SettingsViewModel {
    private final SettingsModel settingsModel;
    private final ObjectProperty<EventType> eventWaitingForKey= new SimpleObjectProperty<>();
    private final BooleanProperty isRemapMode = new SimpleBooleanProperty(false);

    public SettingsViewModel(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public ReadOnlyObjectProperty<InputMap> inputMapProperty() {
        return settingsModel.inputMapProperty();
    }

    public ReadOnlyBooleanProperty isRemapModeProperty() {
        return isRemapMode;
    }

    public ReadOnlyObjectProperty<EventType> eventWaitingForKeyProperty() {
        return eventWaitingForKey;
    }

    public void toggleIsRemapMode() {
        isRemapMode.setValue(!isRemapMode.getValue());
    }

    public void startRemapping(EventType eventType) {
        eventWaitingForKey.setValue(eventType);
    }

    public void finishRemapping(KeyCode keyCode) {
        settingsModel.inputMapProperty().get().bind(keyCode, eventWaitingForKey.getValue());
        eventWaitingForKey.setValue(null);
    }

    public InputMap getInputMap() {
        return inputMapProperty().getValue();
    }
}
