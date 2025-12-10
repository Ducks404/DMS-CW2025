package com.tetris.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class SimpleGameViewInitializer implements GameViewInitializer{
    private final SimpleGameRenderer gameRenderer;
    private Pane gameArea;
    private GridPane gamePanel;
    private GridPane brickPanel;
    private GridPane ghostPanel;
    private Group groupNotifications;

    public SimpleGameViewInitializer(SimpleGameRenderer gameRenderer, Pane gameArea, GridPane gamePanel, GridPane brickPanel, GridPane ghostPanel, Group groupNotifications) {
        this.gameRenderer = gameRenderer;
        this.gameArea = gameArea;
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this. ghostPanel = ghostPanel;
        this. groupNotifications = groupNotifications;
    }

    @Override
    public void setupGamePanes() {
        gameArea.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        groupNotifications.setLayoutY(200);
        for (var notif : groupNotifications.getChildren()) {
            notif.setVisible(false);
        }

        passGamePanes();
    }

    private void passGamePanes() {
        gameRenderer.setGamePanel(gamePanel);
        gameRenderer.setBrickPanel(brickPanel);
        gameRenderer.setGhostPanel(ghostPanel);
        gameRenderer.setGroupNotification(groupNotifications);
    }
}
