package com.tetris.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


/**
 * UI panel displayed when the game is paused.
 * <p>
 * Shows a "Paused" label centered on the screen to inform the player
 * that the game is temporarily halted.
 * </p>
 */
public class PausePanel extends BorderPane {

    /**
     * Constructs a PausePanel with a centered "Paused" label.
     */
    public PausePanel() {
        final Label pauseLabel = new Label("Paused");
        pauseLabel.getStyleClass().add("pauseStyle");
        setCenter(pauseLabel);
    }

}
