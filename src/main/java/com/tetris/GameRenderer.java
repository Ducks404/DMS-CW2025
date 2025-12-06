package com.tetris;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int ROWS_ABOVE_GRID = 2;

    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private final Group groupNotification;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;

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

        rectangles = new Rectangle[brick.brickData().length][brick.brickData()[0].length];
        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.brickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        setBrickPanelLayout(brick);
    }

    private void setBrickPanelLayout(ViewData brick){
        brickPanel.setLayoutX(brick.xPosition() * (brickPanel.getHgap() + BRICK_SIZE));
        brickPanel.setLayoutY((brick.yPosition()-ROWS_ABOVE_GRID) * (brickPanel.getVgap() + BRICK_SIZE));
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = ROWS_ABOVE_GRID; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    public void refreshBrick(ViewData brick) {
        setBrickPanelLayout(brick);
        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                setRectangleData(brick.brickData()[i][j], rectangles[i][j]);
            }
        }
    }

    public void sendNotification(int scoreBonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + scoreBonus);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}
