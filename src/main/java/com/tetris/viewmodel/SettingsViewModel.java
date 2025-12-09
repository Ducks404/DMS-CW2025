package com.tetris.viewmodel;

import com.tetris.input.ControlBinding;
import com.tetris.input.EventType;
import com.tetris.input.InputMap;
import com.tetris.logic.SettingsModel;
import com.tetris.view.ControlLine;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public class SettingsViewModel {
    private final SettingsModel settingsModel;
    private final ListProperty<ControlBinding> controlBindings = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<EventType> eventWaitingForKey= new SimpleObjectProperty<>();
    private final BooleanProperty isRemapMode = new SimpleBooleanProperty(false);

    public SettingsViewModel(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
        refreshControlBindings();
        settingsModel.inputMapProperty().getValue().mapProperty().addListener(new MapChangeListener<KeyCode, EventType>() {
            @Override
            public void onChanged(Change<? extends KeyCode, ? extends EventType> change) {
                refreshControlBindings();
            }
        });
    }

    public ObservableList<ControlBinding> controlBindingsProperty() {
        return controlBindings;
    }

    private void refreshControlBindings() {
        controlBindings.clear();
        InputMap inputMap = settingsModel.getInputMap();
        if (inputMap==null) return;

        for (var entry: inputMap.entrySet()) {
            controlBindings.add(new ControlBinding(entry.getValue(), entry.getKey()));
        }
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

    private void toggleIsRemapMode() {
        isRemapMode.setValue(!isRemapMode.getValue());
    }

    private void startRemapping(EventType eventType) {
        eventWaitingForKey.setValue(eventType);
    }

    private void finishRemapping(KeyCode keyCode) {
        settingsModel.inputMapProperty().getValue().bind(keyCode, eventWaitingForKey.getValue());
        eventWaitingForKey.setValue(null);
    }

    public InputMap getInputMap() {
        return inputMapProperty().getValue();
    }

    public void onChangeKeybindButtonPressed() {
        toggleIsRemapMode();
    }

    public void onKeyPressedDuringRemap(KeyCode keyCode) {
        finishRemapping(keyCode);
    }

    public void onControlLineClicked(EventType eventType) {
        if (isRemapModeProperty().getValue() && eventWaitingForKeyProperty().getValue()==null) {
            startRemapping(eventType);
        }
    }
}
