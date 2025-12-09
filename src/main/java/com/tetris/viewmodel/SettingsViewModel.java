package com.tetris.viewmodel;

import com.tetris.input.ControlBinding;
import com.tetris.input.EventType;
import com.tetris.input.InputMap;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.input.KeyCode;

import javax.naming.ldap.Control;

public class SettingsViewModel {
    private final SettingsModel settingsModel;
    private final ObjectProperty<EventType> eventWaitingForKey= new SimpleObjectProperty<>();
    private final BooleanProperty isRemapMode = new SimpleBooleanProperty(false);

    public SettingsViewModel(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public ObservableList<ControlBinding> controlBindingsProperty() {
        ObservableList<ControlBinding> result = new SimpleListProperty<>();
        for (var entry: settingsModel.inputMapProperty().getValue().entrySet()) {
            result.add(new ControlBinding(entry.getValue(), entry.getKey()));
        }
        return result;
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
