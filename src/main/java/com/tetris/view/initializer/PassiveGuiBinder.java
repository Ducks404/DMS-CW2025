package com.tetris.view.initializer;

import com.tetris.view.renderer.HudRenderer;
import com.tetris.viewmodel.ViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Binds passive (read-only) UI properties to the view model.
 * <p>
 * Handles binding of game state properties (score, pause, game over)
 * to UI elements and manages HUD displays for next and held bricks.
 * </p>
 */
public class PassiveGuiBinder {
    /** Renderer for HUD displays */
    private final HudRenderer hudRenderer = new HudRenderer();

    /**
     * Binds view model properties to UI elements.
     * <p>
     * Updates the score label, pause panel visibility, game over panel visibility,
     * and refreshes HUD previews when brick state changes.
     * </p>
     *
     * @param viewModel the game view model
     * @param scoreLabel the label displaying the current score
     * @param pausePanel the pane shown when game is paused
     * @param gameOverPanel the pane shown when game is over
     * @param nextBrick the grid pane displaying the next brick
     * @param holdBrick the grid pane displaying the held brick
     */
    public void bind(ViewModel viewModel, Label scoreLabel, Pane pausePanel, Pane gameOverPanel, GridPane nextBrick, GridPane holdBrick) {
        // Simple Property Bindings
        scoreLabel.textProperty().bind(viewModel.scoreProperty().asString());
        pausePanel.visibleProperty().bind(viewModel.pauseProperty());
        gameOverPanel.visibleProperty().bind(viewModel.gameOverProperty());

        // Complex Listeners (HUD)
        viewModel.nextBrickProperty().addListener((obs, oldVal, newVal) ->
                hudRenderer.refreshPreview(nextBrick, newVal)
        );
        viewModel.holdBrickProperty().addListener((obs, oldVal, newVal) ->
                hudRenderer.refreshPreview(holdBrick, newVal)
        );
    }

    /**
     * Initializes the HUD display panes.
     * <p>
     * Sets up the initial rendering of next and held brick previews.
     * </p>
     *
     * @param viewModel the game view model
     * @param nextBrick the grid pane for the next brick preview
     * @param holdBrick the grid pane for the held brick preview
     */
    public void initHud(ViewModel viewModel, GridPane nextBrick, GridPane holdBrick) {
        if (viewModel == null) return;
        hudRenderer.initPreview(nextBrick, viewModel.nextBrickProperty().getValue());
        hudRenderer.initPreview(holdBrick, viewModel.holdBrickProperty().getValue());
    }
}