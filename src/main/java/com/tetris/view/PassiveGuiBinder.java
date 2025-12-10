package com.tetris.view;

import com.tetris.view.HudRenderer;
import com.tetris.viewmodel.ViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PassiveGuiBinder {
    private final HudRenderer hudRenderer = new HudRenderer();

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

    public void initHud(ViewModel viewModel, GridPane nextBrick, GridPane holdBrick) {
        if (viewModel == null) return;
        hudRenderer.initPreview(nextBrick, viewModel.nextBrickProperty().getValue());
        hudRenderer.initPreview(holdBrick, viewModel.holdBrickProperty().getValue());
    }
}