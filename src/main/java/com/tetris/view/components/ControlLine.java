package com.tetris.view.components;

import com.tetris.input.ControlBinding;
import com.tetris.util.StringOperations;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * UI component displaying a single control binding.
 * <p>
 * Shows the action name and the key code bound to it, with hover effects
 * to indicate when a binding can be remapped.
 * </p>
 */
public class ControlLine extends HBox {
    /** CSS class for normal state label styling */
    final static String normalStyle = "controlText";
    /** CSS class for hover state label styling */
    final static String hoverStyle = "controlTextHover";
    /** Label displaying the event/action name */
    final Label eventLabel;
    /** Label displaying the bound key code */
    final Label keyLabel;

    /**
     * Constructs a ControlLine for a control binding.
     *
     * @param controlBinding the control binding to display
     */
    public ControlLine(ControlBinding controlBinding) {
        this.eventLabel = new Label(controlBinding.displayName());
        eventLabel.getStyleClass().add(normalStyle);

        this.keyLabel = new Label(StringOperations.toTitleCase(controlBinding.keyCode().toString()));
        keyLabel.getStyleClass().add(normalStyle);

        Region spacer = new Region();
        spacer.setMinWidth(10);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(eventLabel, spacer, keyLabel);
    }

    /**
     * Updates the key label with a new key code.
     *
     * @param keyCode the new key code to display
     */
    public void setKeyLabel(KeyCode keyCode) {
        keyLabel.setText(StringOperations.toTitleCase(keyCode.toString()));
    }

    /**
     * Updates the key label with a string.
     *
     * @param s the string to display as the key label
     */
    public void setKeyLabel(String s) {
        keyLabel.setText(s);
    }

    /**
     * Applies hover styling to indicate remappable state.
     */
    public void onHover() {
        setStyle(eventLabel,hoverStyle);
        setStyle(keyLabel, hoverStyle);
    }

    /**
     * Removes hover styling to indicate normal state.
     */
    public void offHover() {
        setStyle(eventLabel, normalStyle);
        setStyle(keyLabel, normalStyle);
    }

    /**
     * Sets the CSS style class for a label.
     *
     * @param label the label to style
     * @param style the CSS style class name
     */
    private void setStyle(Label label, String style) {
        label.getStyleClass().clear();
        label.getStyleClass().add(style);
    }
}
