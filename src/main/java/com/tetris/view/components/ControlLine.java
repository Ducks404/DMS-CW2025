package com.tetris.view.components;

import com.tetris.input.ControlBinding;
import com.tetris.util.StringOperations;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ControlLine extends HBox {
    final static String normalStyle = "controlText";
    final static String hoverStyle = "controlTextHover";
    final Label eventLabel;
    final Label keyLabel;

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

    public void setKeyLabel(KeyCode keyCode) {
        keyLabel.setText(StringOperations.toTitleCase(keyCode.toString()));
    }

    public void setKeyLabel(String s) {
        keyLabel.setText(s);
    }

    public void onHover() {
        setStyle(eventLabel,hoverStyle);
        setStyle(keyLabel, hoverStyle);
    }
    public void offHover() {
        setStyle(eventLabel, normalStyle);
        setStyle(keyLabel, normalStyle);
    }

    private void setStyle(Label label, String style) {
        label.getStyleClass().clear();
        label.getStyleClass().add(style);
    }
}
