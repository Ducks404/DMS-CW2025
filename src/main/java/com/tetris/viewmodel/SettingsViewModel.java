package com.tetris.viewmodel;

import com.tetris.input.ControlBinding;
import com.tetris.input.EventType;
import com.tetris.input.InputMap;
import com.tetris.logic.SettingsModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

/**
 * View model for game settings and key binding configuration.
 * <p>
 * Manages the display and modification of control key bindings.
 * Provides properties for observing remap mode state and the current key awaiting binding.
 * Automatically updates the control bindings list when the input map changes.
 * </p>
 */
public class SettingsViewModel {
    /** The underlying settings model containing the input map */
    private final SettingsModel settingsModel;
    /** Observable list of current control bindings for display in UI */
    private final ListProperty<ControlBinding> controlBindings = new SimpleListProperty<>(FXCollections.observableArrayList());
    /** The event type currently waiting for a key to be bound to it */
    private final ObjectProperty<EventType> eventWaitingForKey= new SimpleObjectProperty<>();
    /** Whether the user is currently in key remapping mode */
    private final BooleanProperty isRemapMode = new SimpleBooleanProperty(false);

    /**
     * Constructs a SettingsViewModel.
     *
     * @param settingsModel the underlying settings model
     */
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

    /**
     * Gets the observable list of control bindings.
     * <p>
     * This list is automatically updated when the input map changes.
     * </p>
     *
     * @return an ObservableList of ControlBinding objects
     */
    public ObservableList<ControlBinding> controlBindingsProperty() {
        return controlBindings;
    }

    /**
     * Refreshes the control bindings list from the current input map.
     * <p>
     * Clears the current list and rebuilds it from the input map entries.
     * Called automatically when the input map changes.
     * </p>
     */
    private void refreshControlBindings() {
        controlBindings.clear();
        InputMap inputMap = settingsModel.getInputMap();
        if (inputMap==null) return;

        for (var entry: inputMap.entrySet()) {
            controlBindings.add(new ControlBinding(entry.getValue(), entry.getKey()));
        }
    }

    /**
     * Gets the input map property from the settings model.
     *
     * @return a read-only object property containing the current InputMap
     */
    public ReadOnlyObjectProperty<InputMap> inputMapProperty() {
        return settingsModel.inputMapProperty();
    }

    /**
     * Gets the remap mode state property.
     *
     * @return a read-only boolean property indicating if remapping is active
     */
    public ReadOnlyBooleanProperty isRemapModeProperty() {
        return isRemapMode;
    }

    /**
     * Gets the event waiting for key binding property.
     * <p>
     * Non-null when a key binding is being remapped and waiting for a key press.
     * </p>
     *
     * @return a read-only object property containing the EventType awaiting binding
     */
    public ReadOnlyObjectProperty<EventType> eventWaitingForKeyProperty() {
        return eventWaitingForKey;
    }

    /**
     * Toggles the remap mode state.
     * <p>
     * Enables or disables key binding remapping mode in the UI.
     * </p>
     */
    private void toggleIsRemapMode() {
        isRemapMode.setValue(!isRemapMode.getValue());
    }

    /**
     * Starts remapping for a specific event type.
     * <p>
     * Sets the eventWaitingForKey property to indicate the system is waiting
     * for a key press to be bound to this event.
     * </p>
     *
     * @param eventType the event type to start remapping for
     */
    private void startRemapping(EventType eventType) {
        eventWaitingForKey.setValue(eventType);
    }

    /**
     * Completes the remapping process by binding a key to the waiting event.
     * <p>
     * Updates the input map and clears the eventWaitingForKey property.
     * </p>
     *
     * @param keyCode the key code to bind
     */
    private void finishRemapping(KeyCode keyCode) {
        settingsModel.inputMapProperty().getValue().bind(keyCode, eventWaitingForKey.getValue());
        eventWaitingForKey.setValue(null);
    }

    /**
     * Gets the current input map.
     *
     * @return the InputMap from the settings model
     */
    public InputMap getInputMap() {
        return inputMapProperty().getValue();
    }

    /**
     * Called when a key binding change button is pressed.
     * <p>
     * Toggles the remap mode state.
     * </p>
     */
    public void onChangeKeybindButtonPressed() {
        toggleIsRemapMode();
    }

    /**
     * Called when a key is pressed during remapping.
     * <p>
     * Completes the remapping by binding the pressed key to the waiting event.
     * </p>
     *
     * @param keyCode the key code that was pressed
     */
    public void onKeyPressedDuringRemap(KeyCode keyCode) {
        finishRemapping(keyCode);
    }

    /**
     * Called when a control line is clicked during remapping.
     * <p>
     * Starts the remapping process for the clicked event type if in remap mode
     * and no event is currently waiting for a key.
     * </p>
     *
     * @param eventType the event type of the clicked control line
     */
    public void onControlLineClicked(EventType eventType) {
        if (isRemapModeProperty().getValue() && eventWaitingForKeyProperty().getValue()==null) {
            startRemapping(eventType);
        }
    }
}
