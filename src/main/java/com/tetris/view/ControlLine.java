package com.tetris.view;

import com.tetris.input.ControlBinding;
import com.tetris.util.StringOperations;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ControlLine extends HBox {
    final Label eventLabel;
    final Label keyLabel;

    public ControlLine(ControlBinding controlBinding) {
        this.eventLabel = new Label(controlBinding.displayName());
        eventLabel.getStyleClass().add("controlText");

        this.keyLabel = new Label(StringOperations.toTitleCase(controlBinding.keyCode().toString()));
        keyLabel.getStyleClass().add("controlText");

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
}
