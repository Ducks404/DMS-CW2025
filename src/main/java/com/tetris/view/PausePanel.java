package com.tetris.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class PausePanel extends BorderPane {

    public PausePanel() {
        final Label pauseLabel = new Label("P to UNPAUSE");
        pauseLabel.getStyleClass().add("gameOverStyle");
        setCenter(pauseLabel);
    }

}
