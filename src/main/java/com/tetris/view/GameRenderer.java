package com.tetris.view;

import com.tetris.logic.ViewData;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int ROWS_ABOVE_GRID = 2;

    private GridPane gamePanel;
    private GridPane brickPanel;
    private Group groupNotification;
    private GridPane ghostPanel;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] brickMatrix;
    private Rectangle[][] ghostMatrix;

    public GameRenderer() {

    }

    public void setGamePanel(GridPane gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void setBrickPanel(GridPane brickPanel) {
        this.brickPanel = brickPanel;
    }
    public void setGhostPanel(GridPane ghostPanel) {
        this.ghostPanel = ghostPanel;
    }
    public void setGroupNotification(Group groupNotification) {
        this.groupNotification = groupNotification;
    }

    private Rectangle[][] initGrid(GridPane gridPane, int[][] matrix) {
        Rectangle[][] rectangles = new Rectangle[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangles[i][j] = rectangle;
                gridPane.add(rectangle, j, i);
            }
        }
        DrawBrickOperations.drawGrid(matrix, rectangles);

        return rectangles;
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = initGrid(gamePanel, Arrays.copyOfRange(boardMatrix, ROWS_ABOVE_GRID, boardMatrix.length));

        brickMatrix = initGrid(brickPanel, brick.brickData());
        setBrickPanelLayout(brick);

        ghostMatrix = initGrid(ghostPanel, brick.brickData());
        refreshGhost(brick, boardMatrix.length-ROWS_ABOVE_GRID-1);
    }

    private void setBrickPanelLayout(ViewData brick){
        brickPanel.setLayoutX(brick.xPosition() * (brickPanel.getHgap() + BRICK_SIZE));
        brickPanel.setLayoutY((brick.yPosition()-ROWS_ABOVE_GRID) * (brickPanel.getVgap() + BRICK_SIZE));
    }

    public void refreshGameBackground(int[][] board) {
        DrawBrickOperations.drawGrid(Arrays.copyOfRange(board, ROWS_ABOVE_GRID, board.length), displayMatrix);
    }

    public void refreshBrick(ViewData brick) {
        setBrickPanelLayout(brick);
        DrawBrickOperations.drawGrid(brick.brickData(), brickMatrix);
    }

    public void sendNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    public void refreshGhost(ViewData brick, int rowUntilFloor) {
        ghostPanel.setLayoutX(brick.xPosition() * (ghostPanel.getHgap() + BRICK_SIZE));
        ghostPanel.setLayoutY((brick.yPosition() + rowUntilFloor - ROWS_ABOVE_GRID) * (ghostPanel.getVgap() + BRICK_SIZE));
        DrawBrickOperations.drawGrid(brick.brickData(), ghostMatrix, 0.25);
    }

}
