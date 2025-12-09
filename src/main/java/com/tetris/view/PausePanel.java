package com.tetris.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class PausePanel extends BorderPane {

    public PausePanel() {
        final Label pauseLabel = new Label("Paused");
        pauseLabel.getStyleClass().add("pauseStyle");
        setCenter(pauseLabel);
    }

}
