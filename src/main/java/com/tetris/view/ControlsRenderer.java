package com.tetris.view;

import com.tetris.input.ControlBinding;
import com.tetris.util.StringOperations;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ControlsRenderer {
    private final SettingsViewModel settingsViewModel;
    private final ViewModel viewModel;
    private final Pane controlsPanel;
    private final BooleanProperty isRemapMode = new SimpleBooleanProperty();

    public ControlsRenderer(ViewModel viewModel, SettingsViewModel settingsViewModel, Pane controlsPanel) {
        this.viewModel = viewModel;
        this.settingsViewModel = settingsViewModel;
        this.controlsPanel = controlsPanel;

        isRemapMode.bind(settingsViewModel.isRemapModeProperty());
    }

    public void initControlsPanel() {
        for (var entry : settingsViewModel.controlBindingsProperty()) {
            ControlLine line = getControlLine(entry);

            controlsPanel.getChildren().add(line);
        }

        Button controlsButton = getControlsButton();
        controlsPanel.getChildren().add(controlsButton);
    }

    private ControlLine getControlLine(ControlBinding entry) {
        ControlLine line = new ControlLine(entry);
        line.setFocusTraversable(false);

        line.setOnMouseClicked(e -> {
            if (isRemapMode.getValue()) {
                settingsViewModel.startRemapping(entry.eventType());
                line.setKeyLabel("<Press Key>");
                line.requestFocus();
            }
        });

        line.setOnKeyPressed(e -> {
            if (settingsViewModel.eventWaitingForKeyProperty().getValue() != null) {
                settingsViewModel.finishRemapping(e.getCode());
                line.setKeyLabel(e.getCode());
            }
        });
        return line;
    }

    private Button getControlsButton() {
        Button controlsButton = new Button("Change Keybinds");
        controlsButton.setFocusTraversable(false);
        controlsButton.setOnAction(e -> {
            if (!isRemapMode.getValue()) {
                controlsButton.setText("Done");
            } else {
                controlsButton.setText("Change Keybinds");
            }
            settingsViewModel.onChangeKeybindButtonPressed();
            viewModel.onChangeKeybindButtonPressed();
        });
        return controlsButton;
    }
}
