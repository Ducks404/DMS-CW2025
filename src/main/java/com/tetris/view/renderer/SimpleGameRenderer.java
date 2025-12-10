package com.tetris.view.renderer;

import com.tetris.logic.ViewData;
import com.tetris.util.DrawBrickOperations;
import com.tetris.view.components.NotificationPanel;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

/**
 * Renderer for the main game board.
 * <p>
 * Handles all rendering of the game board, falling brick, and ghost preview.
 * Updates visual representation of the board state and brick positions.
 * </p>
 */
public class SimpleGameRenderer {

    /** Size of each brick square in pixels */
    private static final int BRICK_SIZE = 20;
    /** Number of rows above the visible game grid (used for brick spawn area) */
    private static final int ROWS_ABOVE_GRID = 2;

    /** GridPane displaying the game board background */
    private GridPane gamePanel;
    /** GridPane displaying the falling brick */
    private GridPane brickPanel;
    /** Group for notification overlays */
    private Group groupNotification;
    /** GridPane displaying the ghost brick preview */
    private GridPane ghostPanel;

    /** Rectangle grid for the game board display */
    private Rectangle[][] displayMatrix;
    /** Rectangle grid for the falling brick display */
    private Rectangle[][] brickMatrix;
    /** Rectangle grid for the ghost brick preview */
    private Rectangle[][] ghostMatrix;

    /**
     * Constructs a SimpleGameRenderer.
     */
    public SimpleGameRenderer() {}

    /**
     * Sets the game panel for board rendering.
     *
     * @param gamePanel the GridPane to render the game board in
     */
    public void setGamePanel(GridPane gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Sets the brick panel for falling brick rendering.
     *
     * @param brickPanel the GridPane to render the falling brick in
     */
    public void setBrickPanel(GridPane brickPanel) {
        this.brickPanel = brickPanel;
    }

    /**
     * Sets the ghost panel for ghost brick preview rendering.
     *
     * @param ghostPanel the GridPane to render the ghost brick in
     */
    public void setGhostPanel(GridPane ghostPanel) {
        this.ghostPanel = ghostPanel;
    }

    /**
     * Sets the notification group for score notifications.
     *
     * @param groupNotification the Group to add notification panels to
     */
    public void setGroupNotification(Group groupNotification) {
        this.groupNotification = groupNotification;
    }

    /**
     * Initializes a grid pane with rectangles for a matrix.
     * <p>
     * Creates a Rectangle for each cell in the matrix and adds it to the GridPane.
     * </p>
     *
     * @param gridPane the GridPane to populate with rectangles
     * @param matrix the 2D matrix representing the grid layout
     * @return a 2D array of Rectangles matching the matrix dimensions
     */
    private Rectangle[][] initGrid(GridPane gridPane, int[][] matrix) {
        Rectangle[][] rectangles = new Rectangle[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangles[i][j] = rectangle;
                gridPane.add(rectangle, j, i);
            }
        }

        return rectangles;
    }

    /**
     * Initializes the game view with the board and brick.
     * <p>
     * Sets up all display grids and renders the initial board state,
     * falling brick, and ghost preview.
     * </p>
     *
     * @param boardMatrix the game board state
     * @param brick the current brick and its position
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = initGrid(gamePanel, Arrays.copyOfRange(boardMatrix, ROWS_ABOVE_GRID, boardMatrix.length));
        refreshGameBackground(boardMatrix);

        brickMatrix = initGrid(brickPanel, brick.brickData());
        refreshBrick(brick);

        ghostMatrix = initGrid(ghostPanel, brick.brickData());
        refreshGhost(brick, boardMatrix.length-ROWS_ABOVE_GRID-1);
    }

    /**
     * Sets the layout position of a pane.
     *
     * @param panel the Pane to position
     * @param x the x coordinate in pixels
     * @param y the y coordinate in pixels
     */
    private void setPanelLayout(Pane panel, double x, double y) {
        panel.setLayoutX(x);
        panel.setLayoutY(y);
    }

    /**
     * Refreshes the game board background display.
     * <p>
     * Updates the rendering of all static blocks on the board.
     * </p>
     *
     * @param board the game board state to render
     */
    public void refreshGameBackground(int[][] board) {
        DrawBrickOperations.drawGrid(Arrays.copyOfRange(board, ROWS_ABOVE_GRID, board.length), displayMatrix);
    }

    /**
     * Refreshes the falling brick display.
     * <p>
     * Updates the brick panel position and renders the current brick shape.
     * </p>
     *
     * @param brick the brick data including position and shape
     */
    public void refreshBrick(ViewData brick) {
        setPanelLayout(brickPanel,
                brick.xPosition() * (brickPanel.getHgap() + BRICK_SIZE),
                (brick.yPosition()-ROWS_ABOVE_GRID) * (brickPanel.getVgap() + BRICK_SIZE)
        );
        DrawBrickOperations.drawGrid(brick.brickData(), brickMatrix);
    }

    /**
     * Refreshes the ghost brick preview display.
     * <p>
     * Updates the ghost panel position to show where the brick will land
     * and renders a semi-transparent preview of the brick.
     * </p>
     *
     * @param brick the brick data including position and shape
     * @param rowUntilFloor the number of rows until the brick hits the floor
     */
    public void refreshGhost(ViewData brick, int rowUntilFloor) {
        setPanelLayout(ghostPanel,
                brick.xPosition() * (ghostPanel.getHgap() + BRICK_SIZE),
                (brick.yPosition() + rowUntilFloor - ROWS_ABOVE_GRID) * (ghostPanel.getVgap() + BRICK_SIZE)
        );
        DrawBrickOperations.drawGrid(brick.brickData(), ghostMatrix, 0.25);
    }

    /**
     * Sends a score notification to the player.
     * <p>
     * Creates a notification panel showing the score bonus and adds animation.
     * </p>
     *
     * @param scoreBonus the bonus score to display
     */
    public void sendNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    /**
     * Requests focus for the game panel keyboard input.
     */
    public void requestFocus() {
        gamePanel.requestFocus();
    }
}
