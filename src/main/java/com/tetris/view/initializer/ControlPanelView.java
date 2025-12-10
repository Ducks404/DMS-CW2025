package com.tetris.view.initializer;

import com.tetris.input.ControlBinding;
import com.tetris.input.EventType;
import com.tetris.view.components.ControlLine;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * View for the game controls and key binding settings panel.
 * <p>
 * Manages the display of control lines for all game actions and provides
 * a button for toggling key remapping mode. Updates control displays
 * when bindings change.
 * </p>
 */
public class ControlPanelView {
    /** View model providing settings and control binding data */
    private final SettingsViewModel settingsViewModel;
    /** View model providing game state for pause handling */
    private final ViewModel viewModel;
    /** Pane containing all control UI elements */
    private final Pane controlsPanel;
    /** Map from EventType to ControlLine for efficient updates */
    private final Map<EventType, ControlLine> controlLines = new HashMap<>();

    /**
     * Constructs a ControlPanelView.
     *
     * @param viewModel the game view model
     * @param settingsViewModel the settings view model
     * @param controlsPanel the pane to populate with control elements
     */
    public ControlPanelView(ViewModel viewModel, SettingsViewModel settingsViewModel, Pane controlsPanel) {
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

    /**
     * Initializes the controls panel with all control lines and a remap button.
     */
    public void initControlsPanel() {
        for (var entry : settingsViewModel.controlBindingsProperty()) {
            ControlLine line = getControlLine(entry);

            controlsPanel.getChildren().add(line);
            controlLines.put(entry.eventType(), line);
        }

        Button controlsButton = getControlsButton();
        controlsPanel.getChildren().add(controlsButton);
    }

    /**
     * Refreshes the control panel display when bindings change.
     * <p>
     * Updates all key labels and removes hover styling.
     * </p>
     */
    private void refreshControlsPanel() {
        for (var entry : settingsViewModel.controlBindingsProperty()) {
            controlLines.get(entry.eventType()).setKeyLabel(entry.keyCode());
            controlLines.get(entry.eventType()).offHover();
        }
    }

    /**
     * Creates a control line for a key binding.
     *
     * @param entry the control binding to create a line for
     * @return a ControlLine component
     */
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

    /**
     * Creates the button for toggling key remapping mode.
     *
     * @return a Button for changing key bindings
     */
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
