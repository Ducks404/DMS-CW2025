package com.tetris.view.initializer;

import com.tetris.view.renderer.SimpleGameRenderer;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Initializer for the simple game view panes.
 * <p>
 * Configures the layout and sizes of game display panes and passes them
 * to the SimpleGameRenderer for rendering game graphics.
 * </p>
 */
public class SimpleGameViewInitializer implements GameViewInitializer {
    /** The renderer for game graphics */
    private final SimpleGameRenderer gameRenderer;
    /** Main pane containing all game elements */
    private Pane gameArea;
    /** GridPane for the game board background */
    private GridPane gamePanel;
    /** GridPane for the falling brick */
    private GridPane brickPanel;
    /** GridPane for the ghost brick preview */
    private GridPane ghostPanel;
    /** Group for notification overlays */
    private Group groupNotifications;

    /**
     * Constructs a SimpleGameViewInitializer.
     *
     * @param gameRenderer the renderer for game graphics
     * @param gameArea the main pane containing all game elements
     * @param gamePanel the grid pane for the game board
     * @param brickPanel the grid pane for the falling brick
     * @param ghostPanel the grid pane for the ghost brick
     * @param groupNotifications the group for notification overlays
     */
    public SimpleGameViewInitializer(SimpleGameRenderer gameRenderer, Pane gameArea, GridPane gamePanel, GridPane brickPanel, GridPane ghostPanel, Group groupNotifications) {
        this.gameRenderer = gameRenderer;
        this.gameArea = gameArea;
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.ghostPanel = ghostPanel;
        this.groupNotifications = groupNotifications;
    }

    /**
     * Sets up all game display panes.
     * <p>
     * Configures pane sizes and passes all panes to the renderer.
     * </p>
     */
    @Override
    public void setupGamePanes() {
        gameArea.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        groupNotifications.setLayoutY(200);

        passGamePanes();
    }

    /**
     * Passes configured panes to the game renderer.
     */
    private void passGamePanes() {
        gameRenderer.setGamePanel(gamePanel);
        gameRenderer.setBrickPanel(brickPanel);
        gameRenderer.setGhostPanel(ghostPanel);
        gameRenderer.setGroupNotification(groupNotifications);
    }
}
