package com.tetris.view;

import com.tetris.input.ControlBinding;
import com.tetris.input.EventType;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class ControlPanelController {
    private final SettingsViewModel settingsViewModel;
    private final ViewModel viewModel;
    private final Pane controlsPanel;
    private final Map<EventType, ControlLine> controlLines = new HashMap<>();

    public ControlPanelController(ViewModel viewModel, SettingsViewModel settingsViewModel, Pane controlsPanel) {
        this.viewModel = viewModel;
        this.settingsViewModel = settingsViewModel;
        this.controlsPanel = controlsPanel;

        settingsViewModel.controlBindingsProperty().addListener(new ListChangeListener<ControlBinding>() {
            @Override
            public void onChanged(Change<? extends ControlBinding> change) {
                refreshControlsPanel();
            }
        });
        settingsViewModel.eventWaitingForKeyProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal==null) return;
            controlLines.get(newVal).setKeyLabel("<Press Key>");
            controlLines.get(newVal).requestFocus();
        });
    }

    public void initControlsPanel() {
        for (var entry : settingsViewModel.controlBindingsProperty()) {
            ControlLine line = getControlLine(entry);

            controlsPanel.getChildren().add(line);
            controlLines.put(entry.eventType(), line);
        }

        Button controlsButton = getControlsButton();
        controlsPanel.getChildren().add(controlsButton);
    }

    private void refreshControlsPanel() {
        for (var entry : settingsViewModel.controlBindingsProperty()) {
            controlLines.get(entry.eventType()).setKeyLabel(entry.keyCode());
            controlLines.get(entry.eventType()).offHover();
        }
    }

    private ControlLine getControlLine(ControlBinding entry) {
        ControlLine line = new ControlLine(entry);
        line.setFocusTraversable(false);

        line.hoverProperty().addListener(((obs, OldBool, newBool) -> {
            if (settingsViewModel.isRemapModeProperty().getValue() && settingsViewModel.eventWaitingForKeyProperty().getValue()==null) {
                if (newBool) {
                    line.onHover();
                } else {
                    line.offHover();
                }
            }
        }));

        line.setOnMouseClicked(e -> settingsViewModel.onControlLineClicked(entry.eventType()));

        line.setOnKeyPressed(e -> settingsViewModel.onKeyPressedDuringRemap(e.getCode()));
        return line;
    }

    private Button getControlsButton() {
        Button controlsButton = new Button("Change Keybinds");
        controlsButton.setFocusTraversable(false);
        controlsButton.setOnAction(e -> {
            if (!settingsViewModel.isRemapModeProperty().getValue()) {
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
