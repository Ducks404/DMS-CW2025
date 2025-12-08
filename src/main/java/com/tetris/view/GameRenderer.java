package com.tetris.view;

import com.tetris.logic.ViewData;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int ROWS_ABOVE_GRID = 2;

    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private final Group groupNotification;
    private final GridPane ghostPanel;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] brickMatrix;
    private Rectangle[][] ghostMatrix;

    public GameRenderer(Pane gameArea) {
        this.gamePanel = (GridPane) gameArea.lookup("#gamePanel");
        this.brickPanel = (GridPane) gameArea.lookup("#brickPanel");
        this.groupNotification = (Group) gameArea.lookup("#groupNotification");
        this.ghostPanel = (GridPane) gameArea.lookup("#ghostPanel");
//        gameArea.getChildren().add(ghostPanel);
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
                rectangle.setFill(DrawBrickOperations.getFillColor(brick.brickData()[i][j]));
                brickMatrix[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        setBrickPanelLayout(brick);

        ghostMatrix = new Rectangle[brick.brickData().length][brick.brickData()[0].length];
        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(((Color) DrawBrickOperations.getFillColor(brick.brickData()[i][j])).deriveColor(1.0, 1.0, 1.0, 0.25));
                ghostMatrix[i][j] = rectangle;
                ghostPanel.add(rectangle, j, i);
            }
        }
        refreshGhost(brick, 0);
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

    public void refreshGhost(ViewData brick, int rowUntilFloor) {
        ghostPanel.setLayoutX(brick.xPosition() * (ghostPanel.getHgap() + BRICK_SIZE));
        ghostPanel.setLayoutY((brick.yPosition() + rowUntilFloor - ROWS_ABOVE_GRID) * (ghostPanel.getVgap() + BRICK_SIZE));
        DrawBrickOperations.drawGrid(brick.brickData(), ghostMatrix, 0.25);
    }
}
