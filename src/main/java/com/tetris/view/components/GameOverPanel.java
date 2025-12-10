package com.tetris.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


/**
 * UI panel displayed when the game is over.
 * <p>
 * Shows a "GAME OVER" label centered on the screen to inform the player
 * that the game has ended and no further actions can be taken.
 * </p>
 */
public class GameOverPanel extends BorderPane {

    /**
     * Constructs a GameOverPanel with a centered "GAME OVER" label.
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
