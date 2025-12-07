package com.tetris.view;

import com.tetris.logic.ViewData;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int ROWS_ABOVE_GRID = 2;

    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private final Group groupNotification;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] brickMatrix;

    public GameRenderer(GridPane gamePanel, GridPane brickPanel, Group groupNotification) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.groupNotification = groupNotification;
    }


    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = ROWS_ABOVE_GRID; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i-ROWS_ABOVE_GRID);
            }
        }

        brickMatrix = new Rectangle[brick.brickData().length][brick.brickData()[0].length];
        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                brickMatrix[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        DrawBrickOperations.drawGrid(brick.brickData(), brickMatrix);
        setBrickPanelLayout(brick);
    }

    private void setBrickPanelLayout(ViewData brick){
        brickPanel.setLayoutX(brick.xPosition() * (brickPanel.getHgap() + BRICK_SIZE));
        brickPanel.setLayoutY((brick.yPosition()-ROWS_ABOVE_GRID) * (brickPanel.getVgap() + BRICK_SIZE));
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = ROWS_ABOVE_GRID; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                DrawBrickOperations.setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
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
}
