package com.tetris.view;

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

    public void render() {
        for (var entry: settingsViewModel.controlBindingsProperty()) {
            HBox line = new HBox();
            line.setFocusTraversable(false);
            Label eventLabel = new Label(entry.displayName());
            eventLabel.getStyleClass().add("controlText");
            Label keyLabel = new Label(StringOperations.toTitleCase(entry.keyCode().toString()));
            keyLabel.getStyleClass().add("controlText");
            Region spacer = new Region();
            spacer.setMinWidth(10);
            HBox.setHgrow(spacer, Priority.ALWAYS);
            line.getChildren().addAll(eventLabel, spacer, keyLabel);

            line.setOnMouseClicked(e -> {
                if (isRemapMode.getValue()) {
                    settingsViewModel.startRemapping(entry.eventType());
                    keyLabel.setText("<Press Key>");
                    line.requestFocus();
                }
            });

            line.setOnKeyPressed(e -> {
                if (settingsViewModel.eventWaitingForKeyProperty().getValue() != null) {
                    settingsViewModel.finishRemapping(e.getCode());
                    keyLabel.setText(e.getCode().toString());
                }
            });

            controlsPanel.getChildren().add(line);
        }

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
        controlsPanel.getChildren().add(controlsButton);
    }
}
