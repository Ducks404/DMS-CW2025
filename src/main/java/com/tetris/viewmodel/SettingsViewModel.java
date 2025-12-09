package com.tetris.viewmodel;

import com.tetris.input.InputMap;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.*;

public class SettingsViewModel {
    private final SettingsModel settingsModel;
    private final ObjectProperty<String> eventWaitingForKey= new SimpleObjectProperty<>();
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

    public void toggleIsRemapMode() {
        isRemapMode.setValue(!isRemapMode.getValue());
    }
}
